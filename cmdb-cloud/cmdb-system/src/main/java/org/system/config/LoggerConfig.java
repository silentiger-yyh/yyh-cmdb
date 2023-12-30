package org.system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ConstantConfig类
 * 一些全局变量
 *
 * @Author silentiger@yyh
 * @Date 2023-12-30 22:52:18
 */

@Configuration
public class LoggerConfig {

//    public final static Logger logger = LoggerFactory.getLogger("cmdb-system");

    @Bean("logger")
    public Logger logger() {
        return LoggerFactory.getLogger("cmdb-system");
    }
}
