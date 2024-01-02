package org.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.silentiger.api.CommonResult;
import org.silentiger.constant.CmdbConstant;
import org.silentiger.enumeration.ResultCodeEnum;
import org.silentiger.util.LoggerUtil;
import org.silentiger.util.Pinyin4jUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.system.entity.ModelGroup;
import org.system.service.IModelGroupService;

import java.util.Date;
import java.util.List;


/**
 * ModelGroupServiceImpl类
 * 模型分组管理Service实现类
 *
 * @Author silentiger@yyh
 * @Date 2024-01-01 18:05:37
 */

@Service
public class ModelGroupServiceImpl implements IModelGroupService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CmdbConstant.LOGGER_NAME);

    @Override
    public CommonResult<Object> saveModelGroup(ModelGroup modelGroup) {
        boolean flag = modelGroup.get_id() == null; // id为空表示新增的数据
        if (StringUtils.isBlank(modelGroup.getEname())) {
            modelGroup.setEname(Pinyin4jUtil.convertToFirstUpper(modelGroup.getName()));
        }
        Document document = Document.parse(JSON.toJSONString(modelGroup));
        Date date = new Date();
        if (flag) { // 新增
            try {
                // 这里不检查code是否重复，如果在这里检查io次数太多，我把它分解到另一个接口去（根据code获取分组信息）
                ObjectId parent = modelGroup.getParent();
                boolean existParent = modelGroup.getParent() == null; // 如果为null，则为一级分组，否则为二级分组
                Query existQuery = Query.query(Criteria.where("parent").exists(existParent).and("status").is(0));
                existQuery = existParent ? existQuery : existQuery.addCriteria(Criteria.where("parent").is(parent));
                long count = mongoTemplate.count(existQuery, CmdbConstant.MODEL_GROUP_COLLECTION_NAME);
                document.append("createTime", date)
                        .append("updateTime", date)
                        .append("status", 0)
                        .append("parent", modelGroup.getParent())  // 这里如果不这样改写一下，存到数据库的数据回按照对象处理展开存储
                        .append("index", count);
                mongoTemplate.getCollection(CmdbConstant.MODEL_GROUP_COLLECTION_NAME).insertOne(document);
                logger.info("模型分组添加成功: " + document);
            } catch (Exception e) {
                logger.error(LoggerUtil.getFullExpMsg(e));
                return CommonResult.failed("模型分组新增失败");
            }
        } else {  // 修改
            try {
                Query updateQuery = Query.query(Criteria.where("_id").is(modelGroup.get_id()));
                Update updates = new Update();
                document.keySet().forEach(key -> {
                    if (!key.equals("_id") && !key.contains("Time")) {
                        updates.set(key, document.get(key));
                    }
                });
                if (modelGroup.getParent() != null) {
                    updates.set("parent", modelGroup.getParent());
                }
                updates.set("updateTime", date);
                Document orgDoc = mongoTemplate.findAndModify(updateQuery, updates, Document.class, CmdbConstant.MODEL_GROUP_COLLECTION_NAME);
                if (orgDoc == null) {
                    throw new Exception("模型分组修改失败: 记录不存在 " + "(" + document + ")");
                }
                logger.info("模型分组修改成功: " + document);
            } catch (Exception e) {
                logger.error(LoggerUtil.getFullExpMsg(e));
                return CommonResult.failed("模型分组修改失败");
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getMessage());
    }

    @Override
    public CommonResult<Object> getLevel1Groups() {
        try {
            Query query = Query.query(Criteria.where("parent").exists(false).and("status").is(0));
            List<ModelGroup> modelGroups = mongoTemplate.find(query, ModelGroup.class);
            return CommonResult.success(modelGroups);
        } catch (Exception e) {
            logger.error(LoggerUtil.getFullExpMsg(e));
            return CommonResult.failed("一级分组列表查询失败");
        }
    }

    @Override
    public CommonResult<Object> getLevel2Groups(String id) {
        try {
            Query query = Query.query(Criteria.where("parent").is(new ObjectId(id)).and("status").is(0));
            List<ModelGroup> modelGroups = mongoTemplate.find(query, ModelGroup.class);
            return CommonResult.success(modelGroups);
        } catch (Exception e) {
            logger.error(LoggerUtil.getFullExpMsg(e));
            return CommonResult.failed("二级分组列表查询失败");
        }
    }
}
