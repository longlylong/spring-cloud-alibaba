package com.thatday.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        if (enableSwagger) {
            resources.add(swaggerResource("用户模块" , "/v1/user-api/v2/api-docs"));
            resources.add(swaggerResource("后台管理" , "/v1/admin-api/v2/api-docs"));
            resources.add(swaggerResource("公用模块" , "/v1/id-api/v2/api-docs"));
        }

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
