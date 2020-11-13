package com.thatday.user.modules.user.entity;

import com.thatday.user.repository.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 文件夹表
 */
@Data
@Entity(name = "td_dir")
public class Dir extends BaseEntity {

    @Id
    @Column(name = "dir_id")
    private String id;

    @NotNull
    private String title;

    private String icon;
}
