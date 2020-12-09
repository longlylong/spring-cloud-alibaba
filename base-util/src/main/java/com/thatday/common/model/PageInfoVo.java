package com.thatday.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageInfoVo extends RequestPostVo {

    @ApiModelProperty(value = "当前页码")
    private Integer curPage = 0;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;

    public Integer getCurPage() {
        if (curPage == null || curPage <= 0) {
            return 0;
        }
        return curPage;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            return 10;
        }
        return pageSize;
    }

    public static PageInfoVo create(Integer curPage, Integer pageSize) {
        PageInfoVo vo = new PageInfoVo();
        vo.setCurPage(curPage);
        vo.setPageSize(pageSize);
        return vo;
    }

}
