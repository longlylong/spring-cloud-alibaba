package com.thatday.user.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String phone;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @Column(columnDefinition = "timestamp default current_timestamp comment '注册时间'")
    private Timestamp regTime;

    @Column(columnDefinition = "timestamp default current_timestamp comment '登录时间'")
    private Timestamp loginTime;

    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp comment '更新时间'")
    private Timestamp updateTime;
}