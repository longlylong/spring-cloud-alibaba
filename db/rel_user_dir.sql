CREATE TABLE `rel_user_dir`
(
    `user_id` varchar(32) not null comment 'Id主键',
    `dir_id`  varchar(32) not null comment 'Id主键',
    PRIMARY KEY (`user_id`, `dir_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户表';
