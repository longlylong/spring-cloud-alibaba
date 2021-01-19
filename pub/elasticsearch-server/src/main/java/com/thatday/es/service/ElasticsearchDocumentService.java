package com.thatday.es.service;

import com.thatday.es.common.pojo.*;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;

import java.util.List;

/**
 * 文档操作接口
 */
public interface ElasticsearchDocumentService {

    //全文检索
    SearchResponse matchQuery(MatchEntity entity) throws Exception;

    //结构化查询
    SearchResponse termQuery(TermEntity entity) throws Exception;

    //批量新增文档
    RestStatus bulkAddDoc(BulkEntity entity) throws Exception;

    //拼写纠错
    List<String> pSuggest(SuggestEntity entity) throws Exception;

    //搜索推荐（当输入的关键词过多的时候系统进行推荐）
    List<String> tSuggest(SuggestEntity entity) throws Exception;

    //自动补全(完成建议)
    List<String> cSuggest(SuggestEntity entity) throws Exception;


}
