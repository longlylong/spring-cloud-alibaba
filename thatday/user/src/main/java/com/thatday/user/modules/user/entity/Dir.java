package com.thatday.user.modules.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 文件夹表
 */
@Data
@Entity(name = "td_dir")
public class Dir {

    @Id
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String title;

    private String icon;

    @NotNull
    private Date createTime;

    @NotNull
    private Date updateTime;
}
