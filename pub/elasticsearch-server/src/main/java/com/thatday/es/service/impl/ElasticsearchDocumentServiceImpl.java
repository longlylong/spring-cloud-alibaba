package com.thatday.es.service.impl;

import com.thatday.es.common.pojo.*;
import com.thatday.es.common.utils.SearchTools;
import com.thatday.es.service.ElasticsearchDocumentService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestion;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文档操作实现类
 */
@Service("ElasticsearchDocumentServiceImpl")
@RefreshScope
public class ElasticsearchDocumentServiceImpl implements ElasticsearchDocumentService {


    @Resource
    private RestHighLevelClient client;

    /*
     * @Description: 全文检索
     * 使用matchQuery在执行查询时，搜索的词会被分词器分词(重要)
     * @Method: searchMatch
     * @Param: [indexName, key, value]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.search.SearchHit[]
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * >>>>>>>1、构建远程查询
     * >>>>>>>2、构建查询请求
     * >>>>>>>3、构建查询条件
     * >>>>>>>4、设置高亮
     * >>>>>>>5、设置分页
     * >>>>>>>   加入SearchRequest
     * >>>>>>>6、处理高亮
     */
    public SearchResponse matchQuery(MatchEntity entity) throws Exception {
        //定义返回值
        SearchResponse searchResponse;
        //创建查询请求
        SearchRequest searchRequest = new SearchRequest(entity.getIndexName());
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        //获取客户端查询条件
        getClientConditions(entity, searchSourceBuilder);
        //高亮显示
        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(entity.getHighlight()));
        //分页
        searchSourceBuilder.from(entity.getFrom());
        searchSourceBuilder.size(entity.getPageSize());
        //将查询条件放到请求中
        searchRequest.source(searchSourceBuilder);
        //远程查询
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //处理高亮
        SearchTools.setHighResultForClientUI(searchResponse, entity.getHighlight());
        return searchResponse;
    }


    /*
     * @Description:结构化搜索
     * @Method: termQuery
     * @Param: [entity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * 1、构建远程查询
     * 2、定义响应
     * 3、定义查询请求
     * 3、定义查询构建器
     * 4、定义解析器--构建器解析
     * 5、定义高亮
     * 6、定义分页
     * 7、定义排序
     *    加入到SearchRequest
     * 8、高亮渲染
     *
     */
    public SearchResponse termQuery(TermEntity entity) throws Exception {
        //查询请求
        SearchRequest searchRequest = new SearchRequest(entity.getIndexName());
        //构建查询条件构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        //解析器
        XContentParser xContentParser = SearchTools.getXContentParser(entity);
        //查询api
        searchSourceBuilder.parseXContent(xContentParser);
        //高亮
        searchSourceBuilder.highlighter(SearchTools.getHighlightBuilder(entity.getHighlight()));
        //分页
        searchSourceBuilder.from(entity.getFrom());
        searchSourceBuilder.size(entity.getPageSize());
        //排序
        sort(entity, searchSourceBuilder);
        //设置到查询请求
        searchRequest.source(searchSourceBuilder);
        long time = System.currentTimeMillis();
        //远程查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("执行耗时>>>>>>>>" + (System.currentTimeMillis() - time));
        //渲染
        SearchTools.setHighResultForClientUI(searchResponse, entity.getHighlight());
        return searchResponse;
    }


    /*
     * @Description: 自动补全 根据用户的输入联想到可能的词或者短语
     * @Method: suggester
     * @Param: [entity]
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.action.search.SearchResponse
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * 1、定义远程查询
     * 2、定义查询请求（评分排序）
     * 3、定义自动完成构建器（设置前台建议参数）
     * 4、将自动完成构建器加入到查询构建器
     * 5、将查询构建器加入到查询请求
     * 6、获取自动建议的值（数据结构处理）
     */
    public List<String> cSuggest(SuggestEntity entity) throws Exception {
        List<String> list = new ArrayList<>();
        //查询请求
        SearchRequest searchRequest = new SearchRequest(entity.getIndexName());
        //查询构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //定义自动建议构建器
        CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder(entity.getSuggestField());
        completionSuggestionBuilder.text(entity.getSuggestValue());
        completionSuggestionBuilder.skipDuplicates(true);
        completionSuggestionBuilder.size(entity.getSuggestCount());
        //将自动建议构建器加入到查询构建器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest", completionSuggestionBuilder));
        //加入到查询请求
        searchRequest.source(searchSourceBuilder);
        //执行远程查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        CompletionSuggestion completionSuggestion = searchResponse.getSuggest().getSuggestion("czbk-suggest");

        List<CompletionSuggestion.Entry.Option> vList = completionSuggestion.getEntries().get(0).getOptions();

        if (!CollectionUtils.isEmpty(vList)) {
            vList.forEach(item -> list.add(item.getText().toString()));
        }

        return list;
    }

    /*
     * @Description: 拼写纠错
     * @Method: pSuggest
     * @Param: [entity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<java.lang.String>
     * >>>>>>>>>>>>编写思路简短总结>>>>>>>>>>>>>
     * 1、定义远程查询
     * 2、定义查询请求（评分排序）
     * 3、定义自动纠错构建器（设置前台建议参数）
     * 4、将拼写纠错构建器加入到查询构建器
     * 5、将查询构建器加入到查询请求
     * 6、获取拼写纠错的值（数据结构处理）
     */
    @Override
    public List<String> pSuggest(SuggestEntity entity) throws Exception {
        List<String> list = new ArrayList<>();
        //查询请求
        SearchRequest searchRequest = new SearchRequest(entity.getIndexName());
        //查询构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //纠错构建器
        PhraseSuggestionBuilder phraseSuggestionBuilder = new PhraseSuggestionBuilder(entity.getSuggestField());
        phraseSuggestionBuilder.text(entity.getSuggestValue());
        phraseSuggestionBuilder.size(entity.getSuggestCount());
        //将自动建议构建器加入到查询构建器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggest", phraseSuggestionBuilder));
        //加入到查询请求
        searchRequest.source(searchSourceBuilder);
        //执行远程查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        PhraseSuggestion phraseSuggestion = searchResponse.getSuggest().getSuggestion("czbk-suggest");

        List<PhraseSuggestion.Entry.Option> vList = phraseSuggestion.getEntries().get(0).getOptions();

        if (!CollectionUtils.isEmpty(vList)) {
            vList.forEach(item -> list.add(item.getText().toString()));
        }

        return list;
    }


    /*
     * @Description: 搜索推荐（当输入的关键词过多的时候系统进行推荐）
     * @Method: tSuggest
     * @Param: [entity]
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<java.lang.String>
     * 1、定义远程查询
     * 2、定义查询请求（评分排序）
     * 3、定义搜索推荐构建器（设置前台建议参数）
     * 4、将搜索推荐构建器加入到查询构建器
     * 5、将查询构建器加入到查询请求
     * 6、获取搜索推荐的值（数据结构处理）
     */
    public List<String> tSuggest(SuggestEntity entity) throws Exception {
        List<String> list = new ArrayList<>();
        //查询请求
        SearchRequest searchRequest = new SearchRequest(entity.getIndexName());
        //查询构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //定义词条建议器
        TermSuggestionBuilder termSuggestionBuilder = new TermSuggestionBuilder(entity.getSuggestField());
        termSuggestionBuilder.text(entity.getSuggestValue());
        termSuggestionBuilder.analyzer("ik_smart");
        //设置字符串距离算法，使用ngram
        termSuggestionBuilder.stringDistance(TermSuggestionBuilder.StringDistanceImpl.NGRAM);
        //指定查询长度（这个长度一定小于等于实际输入）
        termSuggestionBuilder.minWordLength(4);
        //加入查询构建器
        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("czbk-suggestion", termSuggestionBuilder));
        //放到查询请求
        searchRequest.source(searchSourceBuilder);
        //执行远程查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //处理查询结果
        TermSuggestion termSuggestion = searchResponse.getSuggest().getSuggestion("czbk-suggestion");
        List<TermSuggestion.Entry.Option> vList = termSuggestion.getEntries().get(0).getOptions();
        if (!CollectionUtils.isEmpty(vList)) {
            vList.forEach(item -> list.add(item.getText().toString()));
        }

        return list;
    }


    /*
     * @Description: 获取前端的查询条件
     * @Method: getClientConditions
     * @Param: [entity, searchSourceBuilder]
     * @Update:
     * @since: 1.0.0
     */
    private void getClientConditions(MatchEntity entity, SearchSourceBuilder searchSourceBuilder) {
        //循环前端的查询条件
        for (Map.Entry<String, Object> m : entity.getMap().entrySet()) {
            if (StringUtils.isNotEmpty(m.getKey()) && m.getValue() != null) {
                String key = m.getKey();
                String value = String.valueOf(m.getValue());
                //构造请求体中“query”:{}部分的内容 ,QueryBuilders静态工厂类，方便构造queryBuilder
                //将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
                searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
            }
        }
    }


    /*
     * @Description: 批量新增文档,可自动创建索引、自动创建映射
     * @Method: bulkAddDoc
     * @Param: [indexName, map]
     * @Update:
     * @since: 1.0.0
     */
    @Override
    public RestStatus bulkAddDoc(BulkEntity entity) throws Exception {
        //通过索引构建批量请求对象
        BulkRequest bulkRequest = new BulkRequest(entity.getIndexName());
        //循环前台list文档数据
        for (int i = 0; i < entity.getList().size(); i++) {
            bulkRequest.add(new IndexRequest().source(XContentType.JSON, SearchTools.mapToObjectGroup(entity.getList().get(i))));
        }
        //执行批量新增
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status();
    }

    /*
     * @Description: 排序
     * @Method: sort
     * @Param: [commonEntity, searchSourceBuilder]
     * @Update:
     * @since: 1.0.0
     */
    private void sort(CommonEntity commonEntity, SearchSourceBuilder searchSourceBuilder) {
        String sortField = commonEntity.getSortField();
        if (StringUtils.isNotEmpty(sortField)) {
            //排序,获取前端的order by子句，不区分大小写，参数为空则默认desc
            SortOrder sortOrder = SearchTools.getSortOrder(commonEntity.getSortOrder());
            //执行排序
            searchSourceBuilder.sort(new FieldSortBuilder(commonEntity.getSortField()).order(sortOrder));
        }
    }
}
