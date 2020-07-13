CREATE TABLE `td_user`
(
    `id`          varchar(32) not null unique comment 'Id主键',
    `nickname`    varchar(64) not null comment '昵称',

    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户表';
