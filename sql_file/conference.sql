SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference`
(
    `id`            varchar(40)  NOT NULL COMMENT 'ID',
    `name`          varchar(255) NULL DEFAULT NULL COMMENT '会议名称',
    `create_Time`   Timestamp    NULL DEFAULT NULL COMMENT '创建时间',
    `drop_Time`     Timestamp    NULL DEFAULT NULL COMMENT '结束时间',
    `city`          varchar(255) NULL DEFAULT NULL COMMENT '会议城市',
    `address`       text         NULL COMMENT '会议地址',
    `phone`         varchar(255) NULL DEFAULT NULL COMMENT '会议电话',
    `mail`          varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    `people_Num`    INT          NULL DEFAULT NULL COMMENT '会议规模',
    `department`    varchar(255) NULL DEFAULT NULL COMMENT '所属单位',
    `first_Picture` varchar(255) NULL DEFAULT NULL COMMENT '首图',
    `description`   text         NULL COMMENT '描述',
    `state`         tinyint(2)   NULL DEFAULT 1 COMMENT '状态',
    `language`      tinyint(2)   NULL DEFAULT 0 COMMENT '语言',
    `owner_id`      varchar(40)  NOT NULL COMMENT '会议拥有者id',
    `owner_name`    varchar(40)  NOT NULL COMMENT '会议拥有者姓名',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          varchar(40)  NOT NULL COMMENT 'ID',
    `phone`       varchar(40)  NOT NULL COMMENT '手机号码',
    `name`        varchar(255) NOT NULL COMMENT '用户名称',
    `password`    varchar(255) NULL DEFAULT NULL COMMENT '密码',
    `role`        int(2)       NULL DEFAULT NULL COMMENT '角色',
    `state`       int(2)       NULL DEFAULT 1 COMMENT '启用状态 0是禁用，1是启用',
    `mail`        varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    `avatar`      varchar(255)      DEFAULT NULL COMMENT '头像',
    `create_Time` date         NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `menu_record`;
CREATE TABLE `menu_record`
(
    `id`           varchar(50)  NOT NULL COMMENT 'ID',
    `level`        int(2)       NULL DEFAULT 1 COMMENT '菜单等级',
    `sort`         int(2)       NULL DEFAULT 1 COMMENT '菜单位置',
    `pid`          varchar(50)       DEFAULT NULL COMMENT 'pid',
    `label`        varchar(255) NULL DEFAULT NULL COMMENT '菜单名',
    `children_num` int(2)            DEFAULT NULL COMMENT '子菜单数量',
    `content`      LONGTEXT COMMENT '内容',
    conference_id  varchar(40)  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`
(
    `id`            varchar(50)  NOT NULL COMMENT 'id',
    `title`         varchar(50)  NUll DEFAULT NULL COMMENT '标题',
    `content`       varchar(240) NULL DEFAULT NULL COMMENT '内容',
    `conference_id` varchar(50)  NULL DEFAULT NULL COMMENT '会议id',
    `create_time`   Timestamp    NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `participant`;
CREATE TABLE `participant`
(
    `id`       varchar(50)  NOT NULL COMMENT 'id',
    `name`     varchar(20)  NUll DEFAULT NULL COMMENT '标题',
    `mail`     varchar(240) NULL DEFAULT NULL COMMENT '邮箱',
    `phone`    varchar(20)  NULL DEFAULT NULL COMMENT '电话号码',
    `password` varchar(255) NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `participant_conference`;
CREATE TABLE `participant_conference`
(
    `id`             varchar(50) NOT NULL COMMENT 'id',
    `participant_id` varchar(50) NUll DEFAULT NULL COMMENT '参与者id',
    `conference_id`  varchar(50) NUll DEFAULT NULL COMMENT '会议id',
    `state`          integer     NOT NULL COMMENT '状态',
    `reviewing`      integer     NOT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`
(
    `id`             varchar(50)  NOT NULL COMMENT 'id',
    `name`           varchar(255) NOT NULL COMMENT '文件名',
    `path_name`      varchar(255) NOT NULL COMMENT '存储名',
    `path`           varchar(255) NOT NULL COMMENT '存储路径',
    `submitter_id`   varchar(50)  NOT NULL COMMENT '提交者id',
    `referee_id`     varchar(50)  NUll DEFAULT NULL COMMENT '审核人id',
    `submitter_name` varchar(20)  NOT NULL COMMENT '提交人名字',
    `referee_name`   varchar(20)  NUll DEFAULT NULL COMMENT '审核人名字',
    `conference_id`  varchar(50)  NOT NULL COMMENT '所属会议',
    `state`          integer      NOT NULL COMMENT '状态',
    `create_time`    Timestamp    NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;