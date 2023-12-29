package org.system;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    void contextLoads() {
        Model model = Model.builder()
//                ._id(new ObjectId("658d75c6f13e8d0937423620"))
                .code("hehe")
                .ename("")
                .name("嘻嘻嘻").build();
        modelService.saveModel(model, 0);
    }

}