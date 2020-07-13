CREATE TABLE `td_dir`
(
    `id`          varchar(32) not null unique comment 'Id主键',
    `user_id`     varchar(32) not null comment '用户id',
    `title`       varchar(64) not null comment '文件夹名字',
    `icon`        varchar(256) comment '文件夹图标',

    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文件夹表';
