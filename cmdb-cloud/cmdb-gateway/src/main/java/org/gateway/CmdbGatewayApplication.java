package org.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
// 若要引用公共module的bean，需要让spring容器去扫描才能注入
//@ComponentScan({"org.silentiger", "org.gateway"})
public class CmdbGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmdbGatewayApplication.class, args);
    }
}