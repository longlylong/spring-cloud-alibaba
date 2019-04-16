package com.thatday.common.model;

import lombok.Data;

@Data
public class ExtPageInfo<T, E> extends PageInfo<T> {
    private E ext;
}
