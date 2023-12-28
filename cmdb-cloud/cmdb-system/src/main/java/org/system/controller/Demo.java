package org.system.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo类
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 16:45:30
 */

@RestController
@RequestMapping("demo")
public class Demo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("test/mongo")
    public BsonValue getTime() {
        MongoCollection<Document> model = mongoTemplate.getCollection("model");
        Document document = new Document("code", "CI0001").append("name","配置项1");
        InsertOneResult insertOneResult = model.insertOne(document);
        System.out.println(insertOneResult.getInsertedId());
        return insertOneResult.getInsertedId();
    }
}
