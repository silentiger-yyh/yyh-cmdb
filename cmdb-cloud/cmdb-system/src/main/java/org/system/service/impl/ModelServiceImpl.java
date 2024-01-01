package org.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.silentiger.api.CommonResult;
import org.silentiger.constant.CmdbConstant;
import org.silentiger.enumeration.ResultCodeEnum;
import org.silentiger.util.Pinyin4jUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.system.entity.Model;
import org.system.service.IModelService;

import java.util.Date;
import java.util.List;

/**
 * ModelService类
 * 模型管理Service实现类
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 22:05:09
 */

@Service
public class ModelServiceImpl implements IModelService {

    @Autowired
    private MongoTemplate mongoTemplate;
//    @Autowired
//    private Logger logger;

//    private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);
    private static final Logger logger = LoggerFactory.getLogger(CmdbConstant.LOGGER_NAME);

    @Override
    public CommonResult<Object> saveModel(Model model) {
        if (StringUtils.isBlank(model.getEname())) {
            model.setEname(Pinyin4jUtil.convertToFirstUpper(model.getName()));
        }
        boolean flag = model.get_id() == null;
        // 为空的(null||"")字段不会转
        Document document = Document.parse(JSON.toJSONString(model));
        Date date = new Date();
        if (flag) { // 新增
            document.append("createTime", date)
                    .append("updateTime", date)
                    .append("status", 0);
            try {
                Query query = Query.query(Criteria.where("code").is(model.getCode()).and("status").is(0));
                List<Document> models = mongoTemplate.find(query, Document.class, CmdbConstant.MODEL_COLLECTION_NAME);
                if (!models.isEmpty()) {
                    throw new Exception("新增失败: 编码重复 (" + document.get("code") + ")");
                }
                mongoTemplate.getCollection(CmdbConstant.MODEL_COLLECTION_NAME).insertOne(document);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return CommonResult.failed(ResultCodeEnum.FAILED.getMessage());
            }
        } else  {  // 修改
            try {
                Query query = Query.query(Criteria.where("_id").is(model.get_id()).and("status").is(0));
                Update updates = new Update();
                document.keySet().forEach(key -> {
                    if (!key.equals("_id") && !key.contains("Time")) {
                        updates.set(key, document.get(key));
                    }
                });
                updates.set("updateTime", date);
                // 查询记录并修改，返回旧纪录
                Document updateDoc = mongoTemplate.findAndModify(query, updates, Document.class, CmdbConstant.MODEL_COLLECTION_NAME);
                if (updateDoc == null) {
                    throw new Exception("修改失败: 记录不存在 (" + document + ")");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return CommonResult.failed(ResultCodeEnum.FAILED.getMessage());
            }
        }
        logger.info(flag ? "新增成功(" + document + ")" : "修改成功(" + document + ")");
        return CommonResult.success(ResultCodeEnum.SUCCESS.getMessage());
    }

    @Override
    public CommonResult<Object> getModelInfoById(String id) {
        ObjectId objId = new ObjectId(id);
        Query query = Query.query(Criteria.where("_id").is(objId));
        List<Document> documents = mongoTemplate.find(query, Document.class, CmdbConstant.MODEL_COLLECTION_NAME);
        Document document = null;
        if (!documents.isEmpty()) {
            document = documents.get(0);
        }
        return CommonResult.success(document);
    }

}
