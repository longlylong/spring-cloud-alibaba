package com.thatday.user.modules.user.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 文件夹表
 */
@Data
@Entity(name = "td_dir_group")
public class Dir {

    @Id
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String groupTitle;

    private String groupIcon;

    private Date createTime;

    private Date updateTime;
}
