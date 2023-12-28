package org.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
/**
 * 这个注解可以解决：Test ignored. java.lang.IllegalStateException: Found multiple @SpringBootConfiguration annotated classes [Generic bean:
 */
@ContextConfiguration(classes = CmdbAuthApplication.class)
class CmdbAuthApplicationTest {

    @Test
    void contextLoads() {
        System.out.println(1);
    }

}