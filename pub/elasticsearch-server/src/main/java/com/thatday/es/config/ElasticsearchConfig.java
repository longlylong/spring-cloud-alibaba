package com.thatday.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 配置类：构建Elasticsearch客户端连接配置
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchConfig {

    //es集群ip
    private List<String> cluster;

    /**
     * 超时时间设为5分钟
     */
    private static final int TIME_OUT = 5 * 60 * 1000;

    /**
     * Es高阶客户端构建器
     * 1.0.0
     */
    @Bean
    public RestClientBuilder restClientBuilder() {
        HttpHost[] hosts = new HttpHost[cluster.size()];
        for (int i = 0; i < cluster.size(); i++) {
            String[] c = cluster.get(i).split(":");
            HttpHost h = new HttpHost(c[0], Integer.parseInt(c[1]), "http");
            hosts[i] = h;
        }
        return RestClient.builder(hosts);
    }

    /**
     * 构建Es高阶客户端
     * 1.0.0
     */
    @Bean(destroyMethod = "close")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        restClientBuilder.setRequestConfigCallback(
                requestConfigBuilder -> requestConfigBuilder.setSocketTimeout(TIME_OUT));

        return new RestHighLevelClient(restClientBuilder);
    }

}
