package com.thatday.gateway.filter.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

//@Component
//加上了这个类 Flux<DataBuffer> body = serverHttpRequest.getBody(); 就能取到值了,
// 神奇的一批.据说2.0.6以前直接就能取到的
public class ApiRouteLocator {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {

        //这里就是要匹配的
        String PATH = "/v1/user-api/**" ;
        //这个是服务的地址
        String URI = "lb://user-v1" ;

        RouteLocatorBuilder.Builder serviceProvider = addRouter(builder.routes(), PATH, URI);

        return serviceProvider.build();
    }

    private RouteLocatorBuilder.Builder addRouter(RouteLocatorBuilder.Builder routes, String path, String uri) {
        /*
        route1 是get请求，get请求使用readBody会报错
        route2 是post请求，Content-Type是application/x-www-form-urlencoded，readbody为String.class
        route3 是post请求，Content-Type是application/json,readbody为Object.class
         */
        routes.route(uri + "-route1",
                r -> r.method(HttpMethod.GET)
                        .and()
                        .path(path)
                        .uri(uri))
                .route(uri + "-route2",
                        r -> r.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .and()
                                .method(HttpMethod.POST)
                                .and()
                                .readBody(String.class, readBody -> {
                                    return true;
                                })
                                .and()
                                .path(path)
                                .filters(f -> {
                                    f.filters(authFilter);
                                    return f;
                                })
                                .uri(uri))
                .route(uri + "-route3",
                        r -> r.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .and()
                                .method(HttpMethod.POST)
                                .and()
                                .readBody(Object.class, readBody -> {
                                    return true;
                                })
                                .and()
                                .path(path)
                                .filters(f -> {
                                    f.filters(authFilter);
                                    return f;
                                })
                                .uri(uri));
        return routes;
    }
}
