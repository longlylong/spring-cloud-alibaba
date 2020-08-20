package com.thatday.user.modules.user.entity;

import com.thatday.user.repository.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 文件夹表
 */
@Data
@Entity
@Table(name = "td_dir")
public class Dir extends BaseEntity {

    @Id
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String title;

    private String icon;
}
