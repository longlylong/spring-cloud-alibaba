package com.thatday.tinyid.server.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author King
 */
@Data
public class TinyIdToken {

    private Integer id;

    private String token;

    private String bizType;

    private String remark;

    private Date createTime;

    private Date updateTime;
}