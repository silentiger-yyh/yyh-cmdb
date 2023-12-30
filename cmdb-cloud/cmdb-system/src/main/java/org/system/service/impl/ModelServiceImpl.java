package org.system.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.silentiger.api.CommonResult;
import org.silentiger.constant.CmdbConstant;
import org.silentiger.util.Pinyin4jUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    @Qualifier(value = "logger")
    private Logger logger;

    @Override
    public CommonResult<Object> saveModel(Model model, Integer flag) {
        if (StringUtils.isBlank(model.getEname())) {
            model.setEname(Pinyin4jUtil.convertToFirstUpper(model.getName()));
        }
        // 为空的(null||"")字段不会转
        Document document = Document.parse(JSON.toJSONString(model));
        Date date = new Date();
        switch (flag) {
            case 0: { // 新增
                document.append("_id", new ObjectId())
                        .append("createTime", date)
                        .append("updateTime", date)
                        .append("status", 0);
                try {
                    Query query = Query.query(Criteria.where("code").is(model.getCode()));
                    List<Document> models = mongoTemplate.find(query, Document.class, CmdbConstant.MODEL_COLLECTION_NAME);
                    if (!models.isEmpty()) {
                        throw new Exception("编码重复");
                    }
//                    mongoTemplate.getCollection(CmdbConstant.MODEL_COLLECTION_NAME).insertOne(document);
                    mongoTemplate.insert(document, CmdbConstant.MODEL_COLLECTION_NAME);
                } catch (Exception e) {
                    logger.error("新增失败: "+e.getMessage() + "(" + document.get("code") + ")");
                    return CommonResult.failed("新增失败");
                }
            } break;
            case 1: {  // 修改
                Query query = Query.query(Criteria.where("_id").is(model.get_id()));
                Update updates = new Update();
                document.keySet().forEach(key -> {
                    if (!key.equals("_id") && !key.contains("Time")) {
                        updates.set(key, document.get(key));
                    }
                });
                updates.set("updateTime", date);
                try {
                    mongoTemplate.updateFirst(query,updates, CmdbConstant.MODEL_COLLECTION_NAME);
                } catch (Exception e) {
                    logger.error("修改失败: " + e.getMessage() + "(" + document + ")");
                    return CommonResult.failed("修改失败");
                }
            }break;
            default: break;
        }
        String msg = flag == 0 ? "新增成功(" + document + ")" : "修改成功(" + document + ")";
        logger.info(msg);
        return CommonResult.success("操作成功");
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
