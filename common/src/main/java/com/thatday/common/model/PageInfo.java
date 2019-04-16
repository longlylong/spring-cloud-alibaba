package com.thatday.common.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PageInfo<T> {
    /**
     * 总条数
     */
    private Long total = 0L;
    /**
     * 当前页
     */
    private Integer page = 0;
    /**
     * 分页数据
     */
    private List<T> rows = new LinkedList<>();
}
