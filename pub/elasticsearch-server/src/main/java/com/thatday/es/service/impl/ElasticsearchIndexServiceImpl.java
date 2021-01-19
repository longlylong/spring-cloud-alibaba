package com.thatday.es.service.impl;

import com.thatday.es.common.pojo.IndexAndMappingEntity;
import com.thatday.es.service.ElasticsearchIndexService;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class: ElasticsearchIndexServiceImpl
 * @Package com.itheima.service.impl
 * @Description: 索引操作实现类
 * @Company: http://www.itheima.com/
 */
@Service("ElasticsearchIndexServiceImpl")
public class ElasticsearchIndexServiceImpl implements ElasticsearchIndexService {
    @Resource
    private RestHighLevelClient client;
    private static final int START_OFFSET = 0;
    private static final int MAX_COUNT = 5;

    /*
     * @Description: 新增索引+setting+映射+自定义分词器pinyin
     * setting可以为空（自定义分词器pinyin在setting中）
     * 映射可以为空
     * @Method: addIndexAndMapping
     * @Param: [entity]
     * @Update:
     * @since: 1.0.0
     * @Return: boolean
     *
     */
    @Override
    public boolean addIndexAndMapping(IndexAndMappingEntity entity) throws Exception {
        //设置setting的map
        Map<String, Object> settingMap = new HashMap<>();
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(entity.getIndexName());
        //获取前端参数
        Map<String, Object> map = entity.getMap();
        //循环外层的settings和mapping
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if ("settings".equals(entry.getKey())) {
                if (entry.getValue() instanceof Map && ((Map) entry.getValue()).size() > 0) {
                    request.settings((Map<String, Object>) entry.getValue());
                }
            }
            if ("mapping".equals(entry.getKey())) {
                if (entry.getValue() instanceof Map && ((Map) entry.getValue()).size() > 0) {
                    request.mapping((Map<String, Object>) entry.getValue());
                }

            }
        }
        //创建索引操作客户端
        IndicesClient indices = client.indices();
        //创建响应对象
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        //得到响应结果
        return response.isAcknowledged();

    }

}
