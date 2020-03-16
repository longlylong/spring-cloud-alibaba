package com.ruoyi.project.tool.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    public final static String GateWayUrl = "http://127.0.0.1:7000";

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        resources.add(swaggerResource("用户模块", "/v1/user-api/v2/api-docs"));
        resources.add(swaggerResource("公用模块", "/v1/common-api/v2/api-docs"));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setSwaggerVersion("2.0");
        //setLocation是当前域名访问的。
        //swaggerResource.setLocation(location);
        //这里需要指向网关
        swaggerResource.setUrl(GateWayUrl + location);
        return swaggerResource;
    }
}
