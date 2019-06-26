package com.thatday.user.entity.db;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "varchar(11) default '' comment '手机号码'")
    private String phone;

    @NotNull
    @Column(columnDefinition = "varchar(16) default '' comment '昵称'")
    private String nickname;

    @NotNull
    @Column(columnDefinition = "varchar(128) default '' comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(128) default '' comment '微信的openId'")
    private String weChatOpenId;

    @NotNull
    @Column(columnDefinition = "int(11) default 0 comment '注册设备'")
    private Integer regDevice;

    @NotNull
    @Column(columnDefinition = "timestamp default current_timestamp comment '登录时间'")
    private Date loginTime;

    @NotNull
    @Column(columnDefinition = "timestamp default current_timestamp comment '注册时间'")
    private Date createTime;

    @NotNull
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp comment '更新时间'")
    private Date updateTime;
}
