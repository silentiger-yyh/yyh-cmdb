package org.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.silentiger.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
/**
 * 这个注解可以解决：Test ignored. java.lang.IllegalStateException: Found multiple @SpringBootConfiguration annotated classes [Generic bean:
 */
@ContextConfiguration(classes = CmdbGatewayApplication.class)
class CmdbGatewayApplicationTest {

String key = "access:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE3MDM2NDMzNjQsImp0aSI6IkZGWExWRjNlcVlOaUVacEhENjhZbmVWcmNuZyIsImNsaWVudF9pZCI6InNpbGVudGlnZXItbWFsbC1wb3J0YWwtY2xpZW50IiwidXNlcm5hbWUiOiJhZG1pbiJ9.DVnrWrzpk6t6Fy5HB2NqMhlNox01OfGcumJEZki4Jdg";

    @Autowired
    private RedisService redisService;

    //    @Autowired
//    @Qualifier(value = "redisTemplate")
//    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void contextLoads() {
//        Set access = redisTemplate.keys("access:*");
//        Object o1 = redisTemplate.opsForValue().get("access:" + key);
//        Object o = redisService.get(key);
//        System.out.println(o.toString());
//        Object o = redisService.get("yuyunhu");

//        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) redisTemplate.opsForValue().get(key);
//        System.out.println(token);

        Object o = redisService.get(key);
        String user = o.toString();
        System.out.println(o);
        System.out.println(user);
        JSONObject jsonObject = JSONObject.parseObject(user);
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.get("username"));
    }

}