package com.thatday.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult<T> {
    
    @ApiModelProperty(value = "当前页码")
    private Integer curPage;

    @ApiModelProperty(value = "总条数")
    private Long totalCount;

    @ApiModelProperty(value = "总页码")
    private Integer totalPage;

    private List<T> dataList = new ArrayList<>();
}
