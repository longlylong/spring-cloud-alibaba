package com.thatday.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageInfoVo extends RequestVo {

    @ApiModelProperty(value = "当前页码")
    private Integer curPage = 0;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;

    public static PageInfoVo create(Integer curPage, Integer pageSize) {
        PageInfoVo vo = new PageInfoVo();
        vo.setCurPage(curPage);
        vo.setPageSize(pageSize);
        return vo;
    }

}
