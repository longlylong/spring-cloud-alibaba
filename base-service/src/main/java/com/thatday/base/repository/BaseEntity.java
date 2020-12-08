package com.thatday.base.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 通用字段， is_del 根据需求自行添加
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(name = "create_time", updatable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
