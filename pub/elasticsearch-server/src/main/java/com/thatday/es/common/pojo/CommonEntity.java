package com.thatday.es.common.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 公共实体类
 */
//如果加该注解的字段为null,那么就不序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CommonEntity implements Serializable {

    //页码
    private Integer curPage;

    //每页数据条数
    private Integer pageSize;

    //索引名称
    @NotEmpty(message = "索引名不能为空")
    @ApiModelProperty(value = "索引名")
    private String indexName;

    //高亮列
    private String highlight;

    //排序 DESC  ASC
    private String sortOrder;

    //排序列
    private String sortField;

    public int getFrom() {
        return (getCurPage() - 1) * getPageSize();
    }

    public int getCurPage() {
        if (curPage == null || curPage < 1) {
            return 1;
        }
        return curPage;
    }

    public int getPageSize() {
        if (pageSize == null || pageSize < 1) {
            return 20;
        }
        return pageSize;
    }
}
