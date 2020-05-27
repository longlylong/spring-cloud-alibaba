package com.thatday.user.modules.user.entity.old;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * woop思维表
 */
@Data
//@Entity
public class WoopThought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long fileGroupId;

    @NotNull
    @Column(columnDefinition = "varchar(256) default '' comment 'W-愿望'")
    private String wish;

    @NotNull
    @Column(columnDefinition = "varchar(1024) default '' comment 'O-最好的结果'")
    private String outcome;

    @NotNull
    @Column(columnDefinition = "varchar(1024) default '' comment 'O-设想可能遇到的障碍'")
    private String obstacle;

    @NotNull
    @Column(columnDefinition = "varchar(1024) default '' comment 'P-计划'")
    private String plan;

    @Column(columnDefinition = "timestamp default current_timestamp comment '创建时间'")
    private Date createTime;

    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp comment '更新时间'")
    private Date updateTime;
}
