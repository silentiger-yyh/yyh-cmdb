package org.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.silentiger.api.CommonResult;
import org.silentiger.enumeration.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.system.config.LoggerUtil;
import org.system.service.ICmdbInitialService;

import java.util.function.Consumer;

/**
 * CmdbInitialServiceImpl类
 * 初始化数据库Service实现类
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 18:44:54
 */

@Service
public class CmdbInitialServiceImpl implements ICmdbInitialService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private LoggerUtil logger;

    @Override
    public CommonResult<Object> initCollections(String name) {
        // 断言，抛出异常，如何返回统一的结果呢
        Assert.isTrue(StringUtils.isNotBlank(name), ResultCodeEnum.VALIDATE_FAILED.getMessage());
        boolean initRes = false;
        switch (name) {
            case "all": {
                initRes = initAll();
            }break;
            case "model": {
                initRes = initModel();
            }break;
            case "model_group": {
                initRes = initModelGroup();
            }break;
            case "model_relation": {
                initRes = initModelRelation();
            }break;
            case "model_relation_type": {
                initRes = initModelRelationType();
            }break;
            case "field": {
                initRes = initField();
            }break;
            case "field_type": {
                initRes = initFieldType();
            }break;
            case "field_group": {
                initRes = initFieldGroup();
            }break;
            case "field_rule": {
                initRes = initFieldRule();
            }break;
            case "data": {
                initRes = initData();
            }break;
            case "data_relation": {
                initRes = initDataRelation();
            }break;
            default: break;
        }
        return initRes ? CommonResult.success(null, ResultCodeEnum.SUCCESS.getMessage()) : CommonResult.failed(ResultCodeEnum.FAILED.getMessage());
    }

    public boolean initAll() {
        // 先清除所有集合
        MongoDatabase db = mongoTemplate.getDb();
        MongoIterable<String> collectionNames = db.listCollectionNames();
        collectionNames.forEach((Consumer<? super String>)  collection -> mongoTemplate.getCollection(collection).drop());
        // model
        try {
            MongoCollection<Document> modelCollection = mongoTemplate.getCollection("model");
            Document modelDoc = Document.parse(new BaseDoc().toString())
                    .append("group", "模型分组")
                    .append("parent", "父模型")
                    .append("remodels", "无向关联的模型");
            modelCollection.insertOne(modelDoc);
        } catch (Exception e) {
            logger.error("model初始化异常, " + e.getMessage());
            return false;
        }
        // model_group
        try {
            MongoCollection<Document> modelGroupCollection = mongoTemplate.getCollection("model_group");
            Document modelGroupDoc = Document.parse(new BaseDoc().toString())
                    .append("parentCode", "父级分组")
                    .append("index", "排序号");
            modelGroupCollection.insertOne(modelGroupDoc);
        } catch (Exception e) {
            logger.error("model_group初始化异常, " + e.getMessage());
            return false;
        }
        // model_relation
        try {
            MongoCollection<Document> modelRelationCollection = mongoTemplate.getCollection("model_relation");
            Document modelRelationDoc = Document.parse(new BaseDoc().toString())
                    .append("model1", "模型1，父")
                    .append("model2", "模型2，子")
                    .append("relationType", "关系类型");
            modelRelationCollection.insertOne(modelRelationDoc);
        } catch (Exception e) {
            logger.error("model_relation初始化异常, " + e.getMessage());
            return false;
        }
        // model_relation_type
        try {
            MongoCollection<Document> modelRelationTypeCollection = mongoTemplate.getCollection("model_relation_type");
            Document modelRelationTypeDoc = Document.parse(new BaseDoc().toString())
                    .append("type", "0-无向，1-有向");
            modelRelationTypeCollection.insertOne(modelRelationTypeDoc);
        } catch (Exception e) {
            logger.error("model_relation_type初始化异常, " + e.getMessage());
            return false;
        }
        // field
        try {
            MongoCollection<Document> fieldCollection = mongoTemplate.getCollection("field");
            Document fieldDoc = Document.parse(new BaseDoc().toString())
                    .append("group", "属性分组")
                    .append("type", "属性类型")
                    .append("rule", "校验规则")
                    .append("model", "所属模型")
                    .append("relationType", "关系类型（如果属性类型为关系的话）")
                    .append("remodel", "关联模型")
                    .append("isDisplay", "是否在列表中展示")
                    .append("isSearch", "是否作为搜索条件")
                    .append("isRemote", "是否远程调用获取数据")
                    .append("remoteURI", "远程接口")
                    .append("remoteKey", "接口中对应的key")
                    .append("remoteValue", "接口中value字段")
                    .append("attributes", " 附加属性，比如下拉多选选项")
                    .append("index", "排序号");
            fieldCollection.insertOne(fieldDoc);
        } catch (Exception e) {
            logger.error("field初始化异常, " + e.getMessage());
            return false;
        }
        // field_type
        try {
            MongoCollection<Document> modelFieldTypeCollection = mongoTemplate.getCollection("field_type");
            Document modelFieldTypeDoc = Document.parse(new BaseDoc().toString());
            modelFieldTypeCollection.insertOne(modelFieldTypeDoc);
        } catch (Exception e) {
            logger.error("field_type初始化异常, " + e.getMessage());
            return false;
        }
        // field_rule
        try {
            MongoCollection<Document> modelFieldRuleCollection = mongoTemplate.getCollection("field_rule");
            Document modelFieldRuleDoc = Document.parse(new BaseDoc().toString())
                    .append("fieldType", "属性类型")
                    .append("regular", "校验规则");
            modelFieldRuleCollection.insertOne(modelFieldRuleDoc);
        } catch (Exception e) {
            logger.error("field_rule初始化异常, " + e.getMessage());
            return false;
        }
        // field_group
        try {
            MongoCollection<Document> modelFieldGroupCollection = mongoTemplate.getCollection("field_group");
            Document modelFieldGroupDoc = Document.parse(new BaseDoc().toString())
                    .append("model", "所属模型")
                    .append("index", "排序号");
            modelFieldGroupCollection.insertOne(modelFieldGroupDoc);
        } catch (Exception e) {
            logger.error("field_group初始化异常, " + e.getMessage());
            return false;
        }
        // data
        try {
            MongoCollection<Document> dataCollection = mongoTemplate.getCollection("data");
            Document dataDoc = Document.parse(new BaseDoc().toString())
                    .append("model", "所属模型");
            dataCollection.insertOne(dataDoc);
        } catch (Exception e) {
            logger.error("data初始化异常, " + e.getMessage());
            return false;
        }
        // data_relation
        try {
            MongoCollection<Document> dataRelationCollection = mongoTemplate.getCollection("data_relation");
            Document dataRelationDoc = Document.parse(new BaseDoc().toString())
                    .append("model1", "模型1")
                    .append("model2", "模型2")
                    .append("data1", "实例1")
                    .append("data2", "实例2")
                    .append("type", "关系类型");
            dataRelationCollection.insertOne(dataRelationDoc);
        } catch (Exception e) {
            logger.error("data初始化异常, " + e.getMessage());
            return false;
        }
        // 所有集合创建好之后，创建索引
        try {
            collectionNames.forEach((Consumer<? super String>) collection -> {
                createInboxIndex(collection, "code", true);  // 唯一索引
                // 创建联合索引
                switch (collection) {
                    case "data": {
                        createUnionIndex(collection, "model,updateTime");
                    } break;
                    case "field":{
                        createUnionIndex(collection, "model,index");
                    } break;
                    case "model_group": {
                        createUnionIndex(collection, "parentCode,index");
                    } break;
                    case "data_relation": {
                        createUnionIndex(collection, "model1,data1,model2");
                        createUnionIndex(collection, "model2,data2,model1");
                    } break;
                    default: break;
                }
            });
        } catch (Exception e) {
            logger.error("索引创建失败, " + e.getMessage());
            return false;
        }
        logger.info("所有集合已完成初始化！");
        return true;
    }


    private boolean initDataRelation() {
        return false;
    }

    private boolean initData() {
        return false;
    }

    private boolean initFieldRule() {
        return false;
    }

    private boolean initFieldGroup() {
        return false;
    }

    private boolean initFieldType() {
        return false;
    }

    private boolean initField() {
        return false;
    }

    private boolean initModelRelationType() {
        return false;
    }

    private boolean initModelRelation() {
        return false;
    }

    private boolean initModelGroup() {
        return false;
    }

    private boolean initModel() {
        return false;
    }

    /**
     * 创建联合索引
     *
     * @param collectionName 集合名称
     * @param keys           字段
     */
    private void createUnionIndex(String collectionName, String keys) {
        try {
            Index index = new Index();
            for (String key : keys.split(",")) {
                index = index.on(key.trim(), Sort.Direction.ASC);
            }
            mongoTemplate.indexOps(collectionName).ensureIndex(index);
        } catch (Exception ex) {
            throw new RuntimeException("建立索引异常，" + ex.getMessage());
        }
    }

    /**
     * 创建单个索引
     *
     * @param collectionName 集合名称
     * @param key            需要建立索引的字段
     * @param unique         是否唯一，true唯一
     */
    public void createInboxIndex(String collectionName, String key, boolean unique) {
        try {
            Index index = new Index().on(key.trim(), Sort.Direction.ASC);
            if (unique) {
                index.unique();
            }
            mongoTemplate.indexOps(collectionName).ensureIndex(index);

        } catch (Exception ex) {
            throw new RuntimeException("建立索引异常，" + ex.getMessage());
        }
    }

}

@Data
class BaseDoc {
    String code = "init";
    String name = "初始化数据";
    String ename = "initial data";
    String createTime = "记录创建时间";
    String updateTime = "记录修改时间";
    String status = "记录的状态，0-正常，1-删除，其他数字备用";

    @Override
    public String toString() {
        return JSON.toJSON(this).toString();
    }
}