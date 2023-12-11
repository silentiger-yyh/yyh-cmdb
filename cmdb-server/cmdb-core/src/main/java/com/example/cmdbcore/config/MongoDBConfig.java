package com.example.cmdbcore.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class MongoDBConfig {
    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://yuyunhu:123456@192.168.83.141:27017/yyh-cmdb?connectTimeoutMS=2000");
        return MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToSocketSettings(
                        builder -> builder.connectTimeout(5, SECONDS)
                ).build()
        );
    }
}
