SET
FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference`
(
    `id`            varchar(40) NOT NULL COMMENT 'ID',
    `name`          varchar(255) NULL DEFAULT NULL COMMENT '会议名称',
    `create_Time`   Timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `drop_Time`     Timestamp NULL DEFAULT NULL COMMENT '结束时间',
    `city`          varchar(255) NULL DEFAULT NULL COMMENT '会议城市',
    `address`       text NULL COMMENT '会议地址',
    `phone`         varchar(255) NULL DEFAULT NULL COMMENT '会议电话',
    `mail`          varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    `people_Num`    INT NULL DEFAULT NULL COMMENT '会议规模',
    `department`    varchar(255) NULL DEFAULT NULL COMMENT '所属单位',
    `first_Picture` varchar(255) NULL DEFAULT NULL COMMENT '首图',
    `description`   text NULL COMMENT '描述',
    `state`         tinyint(2) NULL DEFAULT 1 COMMENT '状态',
    `language`      tinyint(2) NULL DEFAULT 0 COMMENT '语言',
    `owner_id`      varchar(40) NOT NULL COMMENT '会议拥有者id',
    `owner_name`    varchar(40) NOT NULL COMMENT '会议拥有者姓名',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          varchar(40)  NOT NULL COMMENT 'ID',
    `phone`       varchar(40)  NOT NULL COMMENT '手机号码',
    `name`        varchar(255) NOT NULL COMMENT '用户名称',
    `password`    varchar(255) NULL DEFAULT NULL COMMENT '密码',
    `role`        int(2) NULL DEFAULT NULL COMMENT '角色',
    `state`       int(2) NULL DEFAULT 1 COMMENT '启用状态 0是禁用，1是启用',
    `mail`        varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    `avatar`      varchar(255) DEFAULT NULL COMMENT '头像',
    `create_Time` date NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;