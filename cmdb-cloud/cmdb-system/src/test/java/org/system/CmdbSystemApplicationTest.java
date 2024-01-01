package org.system;

import com.mongodb.client.model.IndexOptions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.test.context.ContextConfiguration;
import org.system.entity.Model;
import org.system.service.IModelService;

@SpringBootTest
/**
 * 这个注解可以解决：Test ignored. java.lang.IllegalStateException: Found multiple @SpringBootConfiguration annotated classes [Generic bean:
 */
@ContextConfiguration(classes = CmdbSystemApplication.class)
class CmdbSystemApplicationTest {
    @Autowired
    private IModelService modelService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        Model model = Model.builder()
//                ._id(new ObjectId("658d75c6f13e8d0937423620"))
                .code("hehe")
                .ename("")
                .name("嘻嘻嘻").build();
        modelService.saveModel(model, 0);
    }

    @Test
    void testCreateIndex() {

        for (String collectionName : mongoTemplate.getCollectionNames()) {
            Index index = new Index().on("code", Sort.Direction.ASC).unique();
            IndexOptions indexOptions = new IndexOptions().unique(true);
//        mongoTemplate.indexOps("model_group").ensureIndex(index);
            mongoTemplate.getCollection(collectionName).createIndex(index.getIndexKeys(), indexOptions);
        }
    }

}