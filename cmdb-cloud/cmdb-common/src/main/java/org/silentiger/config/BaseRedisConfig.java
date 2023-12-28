package org.silentiger.config;


import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.silentiger.service.RedisService;
import org.silentiger.service.impl.RedisServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 项目全局Redis配置
 *
 * @Author silentiger@yyh
 * @Date 2023-12-20 19:35:37
 */

/**
 * 没有@Configuration注解的bean,代表不会被CGLIB处理，每次调用bean的方法都会返回一个新的对象
 * 有@Configuration注解的bean，代表是一个代理对象，@Bean修饰的方法只被调用一次，每次返回都是一个对象
 */

//@Configuration
public class BaseRedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = serializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        // value序列化方式采用fastjson
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    //    /**
//     * 此方法不能用@Ben注解，避免替换Spring容器中的同类型对象
//     */
    public GenericFastJsonRedisSerializer serializer() {
        return new GenericFastJsonRedisSerializer();
    }
//    @Bean
//    public RedisSerializer<Object> redisSerializer() {
//        //创建JSON序列化器
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //必须设置，否则无法将JSON转化为对象，会转化成Map类型
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(objectMapper);
//        return serializer;
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        //设置Redis缓存有效期为1天
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())).entryTtl(Duration.ofDays(1));
//        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
//    }


    @Bean
    public RedisService redisService(){
        return new RedisServiceImpl();
    }

}
