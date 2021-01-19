package com.thatday.es.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.thatday.es.common.pojo.CommonEntity;
import com.thatday.es.common.pojo.TermEntity;
import com.thatday.es.common.thread.ResponseThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SearchTools 查询服务工具类
 */
public class SearchTools {

    /*
     * @Description: 高亮前端显示组装，SearchResponse传递引用
     * 为什么二次处理高亮？
     * 原因：被设置的高亮列，es自动放到了highlight属性中；这个属性渲染了高亮的着色
     * 数据传输的时候，我们需要将它取出来
     * 覆盖到我们的_source中
     * @Method: setHighResult
     * @Param: [searchResponse, commonEntity]
     * @Update:
     * @since: 1.0.0
     */
    public static void setHighResultForClientUI(SearchResponse searchResponse, String highlightField) {
        if (StringUtils.isNoneEmpty(highlightField)) {
            for (SearchHit hit : searchResponse.getHits()) {
                //获取高亮字段map
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                //获取到具体的高亮列
                HighlightField highlightFieldName = highlightFields.get(highlightField);
                //getSourceAsMap拿到具体的数据
                Map<String, Object> source = hit.getSourceAsMap();
                if (highlightFieldName != null) {
                    //获取渲染后的文本
                    Text[] fragments = highlightFieldName.fragments();
                    String name = "";
                    for (Text text : fragments) {
                        name += text;
                    }
                    source.put(highlightField, name);   //高亮字段替换掉原本的内容
                }
            }
        }
    }

    /*
     * @Description: 获取高亮构建器
     * @Method:
     * @Param:
     * @Update:
     * @since: 1.0.0
     */
    public static HighlightBuilder getHighlightBuilder(String highlightField) {

        // 设置高亮,使用默认的highlighter高亮器,默认em斜体
        HighlightBuilder highlightBuilder = new HighlightBuilder(); //生成高亮查询器
        highlightBuilder.field(highlightField);      //高亮查询字段
        highlightBuilder.requireFieldMatch(false);     //如果要多个字段高亮,这项要为false
        highlightBuilder.preTags("<span style= " +
                "color:red;font-weight:bold;font-size:15px;" +
                ">");   //高亮设置
        highlightBuilder.postTags("</span>");
        //下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
        return highlightBuilder;
    }


    /*
     * @Description: 获取排序  DESC  ASC 前端不区分大小写，默认返回DESC
     * @Method: getSortOrder
     * @Param: [sortOrder]
     * @Update:
     * @since: 1.0.0
     */
    public static SortOrder getSortOrder(String sortOrder) {
        SortOrder so;
        sortOrder = StringUtils.isEmpty(sortOrder) ? "" : sortOrder.toLowerCase();
        if ("asc".equals(sortOrder)) {
            so = SortOrder.ASC;
        } else {
            so = SortOrder.DESC;
        }
        return so;
    }

    /*
     * @Description: MAP转数组
     * @Method: mapToObjectGropu
     * @Param: [data]
     * @Update:
     * @since: 1.0.0
     * @Return: java.lang.Object[]
     */
    public static Object[] mapToObjectGroup(Map<String, Object> data) {
        List<Object> args = new ArrayList<>();
        if (data != null) {
            data.forEach((key, value) -> {
                args.add(key);
                args.add(value);
            });
        }

        return args.toArray();
    }

    /*
     * @Description: 根据客户端传来的查询参数（标准的DSL语句）构建XContentParser
     * @Method: getXContentParser
     * @Param: []
     * @Update:
     * @since: 1.0.0
     * @Return: org.elasticsearch.common.xcontent.XContentParser
     *
     */
    public static XContentParser getXContentParser(TermEntity entity) throws IOException {

        //构建SearchModule对象置 ,通过构造器注册解析器、建议器、排序等
        SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());
        //获取注册成功的注册解析器、建议器、排序
        NamedXContentRegistry registry = new NamedXContentRegistry(searchModule.getNamedXContents());
        //将前端传来的DSL参数通过解析解解析
        XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(registry, LoggingDeprecationHandler.INSTANCE, JSONObject.toJSONString(entity.getMap()));
        return parser;
    }

    /*
     * @Description: 将查询出来的数据放到本地局部线程变量中
     * @Method: setResponseThreadLocal
     * @Param: [response]
     * @Update:
     * @since: 1.0.0
     */
    public static void setResponseThreadLocal(SearchResponse response) {
        //查询出来的数据
        SearchHit[] sh = response.getHits().getHits();
        //定义list用来接收所有Resource下面的结果集
        List<JSONObject> list = new ArrayList<>();
        if (sh != null) {
            for (SearchHit hit : sh) {
                list.add(JSONObject.parseObject(hit.getSourceAsString()));
            }
            //将数据放入到本地线程
            ResponseThreadLocal.set(list);
        }
    }

}