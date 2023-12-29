package org.system.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * JacksonConfig类
 * ObjectId序列化与反序列化配置
 *
 * @Author silentiger@yyh
 * @Date 2023-12-30 00:04:39
 */

@Configuration
public class JacksonConfig implements InitializingBean {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(ObjectId.class, ToStringSerializer.instance);
        simpleModule.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    public class ObjectIdDeserializer  extends JsonDeserializer<ObjectId> {
        @Override
        public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new ObjectId(p.getText());
        }
    }
}