package com.thatday.user.entity.db.old;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * ABC情绪表
 */
@Data
//@Entity
public class ABCEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long fileGroupId;

    @NotNull
    @Column(columnDefinition = "varchar(256) default '' comment 'A-经历或发生的事情'")
    private String aThing;

    @NotNull
    @Column(columnDefinition = "varchar(1024) default '' comment 'B-观念或想法'")
    private String bIdea;

    @NotNull
    @Column(columnDefinition = "varchar(1024) default '' comment 'C-感受或行为'")
    private String cBehavior;

    @Column(columnDefinition = "timestamp default current_timestamp comment '创建时间'")
    private Date createTime;

    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp comment '更新时间'")
    private Date updateTime;
}
