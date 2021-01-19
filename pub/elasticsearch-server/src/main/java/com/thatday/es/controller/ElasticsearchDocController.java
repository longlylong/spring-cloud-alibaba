package com.thatday.es.controller;

import com.thatday.common.exception.GlobalException;
import com.thatday.common.model.Result;
import com.thatday.es.common.pojo.*;
import com.thatday.es.service.ElasticsearchDocumentService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 文档操作控制器
 */
@RestController
@RequestMapping("/docs")
@Log4j2
public class ElasticsearchDocController {

    @Autowired
    ElasticsearchDocumentService elasticsearchDocumentService;

    /**
     * 全文检索
     * matchQuery
     * 1.0.0
     */
    @PostMapping(value = "/matchQuery")
    public Result<SearchHits> matchQuery(@Valid @RequestBody MatchEntity entity) {
        try {
            //远程调用（全文检索）
            SearchResponse searchResponse = elasticsearchDocumentService.matchQuery(entity);
            long total = searchResponse.getHits().getTotalHits().value;
            long cSize = searchResponse.getHits().getHits().length;
            log.info("matchQuery>>>CommonEntity{}", entity);
            log.info("matchQuery>>>total:{},cur:{}", total, cSize);
            return Result.buildSuccess(searchResponse.getHits());
        } catch (Exception e) {
            throw GlobalException.createError(e.getMessage());
        }
    }

    /**
     * 结构化搜索（查询手机在2000-3000元之间、京东物流发货，按照评价进行排序）
     * termQuery
     * 1.0.0
     */
    @PostMapping(value = "/termQuery")
    public Result<SearchHits> termQuery(@Valid @RequestBody TermEntity entity) {
        try {
            //通过高阶API调用批量新增操作方法
            SearchResponse searchResponse = elasticsearchDocumentService.termQuery(entity);
            long total = searchResponse.getHits().getTotalHits().value;
            long cSize = searchResponse.getHits().getHits().length;
            log.info("termQuery>>>CommonEntity{}", entity);
            log.info("termQuery>>>total:{},cur:{}", total, cSize);
            return Result.buildSuccess(searchResponse.getHits());
        } catch (Exception e) {
            throw GlobalException.createError(e.getMessage());
        }
    }


    /**
     * 批量新增文档,可自动创建索引、自动创建映射
     * [indexName, map]
     * 1.0.0
     */
    @PostMapping(value = "/bulkAddDoc")
    public Result<RestStatus> bulkAddDoc(@Valid @RequestBody BulkEntity entity) {
        try {
            //通过高阶API调用批量新增操作方法
            RestStatus result = elasticsearchDocumentService.bulkAddDoc(entity);
            log.info("bulkAddDoc>>>CommonEntity{}", entity);
            log.info("bulkAddDoc>>>result:{}", result);
            return Result.buildSuccess(result);
        } catch (Exception e) {
            throw GlobalException.createError(e.getMessage());
        }
    }

    /**
     * 自动补全
     * suggester
     * 1.0.0
     */
    @PostMapping(value = "/cSuggest")
    public Result<List<String>> cSuggest(@Valid @RequestBody SuggestEntity entity) {
        try {
            List<String> result = elasticsearchDocumentService.cSuggest(entity);
            log.info("cSuggest>>>CommonEntity{}", entity);
            log.info("cSuggest>>>result:{}", result);
            return Result.buildSuccess(result);
        } catch (Exception e) {
            throw GlobalException.createError(e.getMessage());
        }
    }


    /**
     * 拼写纠错
     * suggester2
     * [entity]
     * 1.0.0
     */
    @PostMapping(value = "/pSuggest")
    public Result<List<String>> pSuggest(@RequestBody SuggestEntity entity) {
        try {
            List<String> result = elasticsearchDocumentService.pSuggest(entity);
            log.info("pSuggest>>>CommonEntity{}", entity);
            log.info("pSuggest>>>result:{}", result);
            return Result.buildSuccess(result);
        } catch (Exception e) {
            throw GlobalException.createError(e.getMessage());
        }
    }


    /**
     * 搜索推荐（当输入的关键词过多的时候系统进行推荐）
     * tSuggest
     * 1.0.0
     */
    @PostMapping(value = "/tSuggest")
    public Result<List<String>> tSuggest(@RequestBody SuggestEntity entity) {
        try {
            List<String> result = elasticsearchDocumentService.tSuggest(entity);
            log.info("tSuggest>>>CommonEntity{}", entity);
            log.info("tSuggest>>>result:{}", result);
            return Result.buildSuccess(result);
        } catch (Exception e) {
            throw GlobalException.createError(e.getMessage());
        }
    }
}
