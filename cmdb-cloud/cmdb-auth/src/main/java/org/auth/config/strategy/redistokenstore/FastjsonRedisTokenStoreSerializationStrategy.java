package org.auth.config.strategy.redistokenstore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.alibaba.nacos.shaded.com.google.common.base.Preconditions;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.SerializationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.nio.charset.StandardCharsets;

/**
 * token存入redis的序列化策略，修改oauth2默认序列化策略
 *
 * @Author silentiger@yyh
 * @Date 2023-12-23 17:49:37
 */

public class FastjsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    private static ParserConfig config = new ParserConfig();

    static {
        init();
    }

    protected static void init() {
        //Custom oauth2 serialization: DefaultOAuth2RefreshToken has no setValue method, which will cause JSON serialization to null
        config.setAutoTypeSupport(true);//Open AutoType
        //Custom DefaultOauth2RefreshTokenSerializer deserialization
        config.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOauth2RefreshTokenSerializer());
        //Custom OAuth2Authentication deserialization
        config.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationSerializer());
        //Add autotype whitelist

        config.addAccept("org.springframework.security.oauth2.provider.");
        config.addAccept("org.springframework.security.oauth2.provider.client");
        config.addAccept("org.springframework.security.authentication");
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication", OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails", BaseClientDetails.class);
        TypeUtils.addMapping("org.springframework.security.authentication.RememberMeAuthenticationToken", RememberMeAuthenticationToken.class);
        config.addAccept("org.springframework.security.oauth2.common.");
        TypeUtils.addMapping("org.springframework.security.oauth2.common.DefaultOAuth2AccessToken", DefaultOAuth2AccessToken.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken", DefaultExpiringOAuth2RefreshToken.class);

//        config.addAccept("com.aicloud.common.core.entity");
//        TypeUtils.addMapping("com.aicloud.common.core.entity.BaseUser", BaseUser.class);

        config.addAccept("org.springframework.security.web.authentication.preauth");
        TypeUtils.addMapping("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken", PreAuthenticatedAuthenticationToken.class);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> aClass) {
        Preconditions.checkArgument(aClass != null, "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), aClass, config);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }


    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }

    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return new byte[0];
        }

        try {
            return JSON.toJSONBytes(o, SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return data.getBytes(StandardCharsets.UTF_8);
    }
}

