package com.example.cmdbcore;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class CmdbCoreApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void contextLoads() {
        MongoCollection<Document> model = mongoTemplate.getCollection("model");
        Document document = new Document("code", "CI0001").append("name","配置项1");
        InsertOneResult insertOneResult = model.insertOne(document);
        System.out.println(insertOneResult.getInsertedId());
    }

}
