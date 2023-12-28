package org.gateway.config.knife4j;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 这里就是从路由配置中获取各个服务的路由信息
 * author: silentiger@yyh
 * date: 2023-12-16
 */
@Component
@RequiredArgsConstructor
public class Knife4jResourceProvider implements SwaggerResourcesProvider {
    // swagger2默认的url后缀
    private static final String SWAGGER2_URL = "/v2/api-docs";
    // 路由定位器
    private final RouteLocator routeLocator;
    // 网关应用名称
    @Value("${spring.application.name}")
    private String gatewayName;
    // nacos客户端
    @Autowired
    private DiscoveryClient discoveryClient;
    /**
     * 获取 Swagger 资源
     *    获取条件：所有配置的网关路由服务 - 排除网关地址 - 未注册到nacos的服务或者nacos存在异常的服务
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        // 1. 获取路由Uri 中的Host=> 服务注册则为服务名=> app-service001
        routeLocator.getRoutes()
                .filter(route -> route.getId() != null)
                //过滤掉网关的文档信息
                .filter(route -> !gatewayName.equals(route.getId()))
                //根据服务状态注入api文档信息
                .filter(route -> !CollectionUtils.isEmpty(discoveryClient.getInstances(route.getUri().getHost())))
                .subscribe(route -> routeHosts.add(route.getId())); // 获取routeID作为"/v2/api-docs"的前缀
        // 2. 创建自定义资源
        for (String routeHost : routeHosts) {
            String serviceUrl = "/" + routeHost + SWAGGER2_URL; // 后台访问添加服务名前缀
            SwaggerResource swaggerResource = new SwaggerResource(); // 创建Swagger 资源
            swaggerResource.setUrl(serviceUrl); // 设置访问地址
            swaggerResource.setName(routeHost); // 设置名称
            swaggerResource.setSwaggerVersion("2.0.0");
            resources.add(swaggerResource);
        }
        return resources;
    }

}
