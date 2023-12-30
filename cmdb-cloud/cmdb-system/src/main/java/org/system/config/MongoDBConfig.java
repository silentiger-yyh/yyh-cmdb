package org.system.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * MongoDBConfig类
 * Mongodb配置文件，与yml配置二选其一即可
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 17:28:31
 */

@Configuration
public class MongoDBConfig {
//    @Bean
//    public MongoClient mongoClient() {
//        ConnectionString connectionString = new ConnectionString("mongodb://yuyunhu:123456@mongo:27017/silentiger-cmdb-log?connectTimeoutMS=2000");
//        return MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyConnectionString(connectionString)
//                        .applyToSocketSettings(
//                                builder -> builder.connectTimeout(5, SECONDS)
//                        ).build()
//        );
//    }

    /**
     * 开启事务支持
     * @param mongoDbFactory
     * @return
     */
    @Bean
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory);
    }
}