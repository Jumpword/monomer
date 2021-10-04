create table `demo_user`(
`id` bigint(20) primary key not null auto_increment COMMENT 'ID主键',
`user_name` varchar(128) NOT NULL COMMENT '名字',
`user_code` varchar(32)  NOT NULL COMMENT '用户账户',
`password` varchar(64)  NOT NULL COMMENT '用户密码',
`version`  int(8) DEFAULT  NULL COMMENT '乐观锁',
`create_time`  datetime DEFAULT  NULL COMMENT '创建时间',
`update_time`  datetime DEFAULT  NULL COMMENT '更新时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
                          user_id           bigint(20)      not null auto_increment    comment '用户ID',
                          dept_id           bigint(20)      default null               comment '部门ID',
                          user_name         varchar(30)     not null                   comment '用户账号',
                          nick_name         varchar(30)     not null                   comment '用户昵称',
                          user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
                          email             varchar(50)     default ''                 comment '用户邮箱',
                          phonenumber       varchar(11)     default ''                 comment '手机号码',
                          sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
                          avatar            varchar(100)    default ''                 comment '头像地址',
                          password          varchar(100)    default ''                 comment '密码',
                          status            char(1)         default '0'                comment '帐号状态（0停用 1正常）',
                          del_flag          char(1)         default '0'                comment '删除标志（0代表删除 1代表存在）',
                          login_ip          varchar(128)    default ''                 comment '最后登录IP',
                          login_date        datetime                                   comment '最后登录时间',
                          create_by         varchar(64)     default ''                 comment '创建者',
                          create_time       datetime                                   comment '创建时间',
                          update_by         varchar(64)     default ''                 comment '更新者',
                          update_time       datetime                                   comment '更新时间',
                          remark            varchar(500)    default null               comment '备注',
                          primary key (user_id)
) engine=innodb DEFAULT CHARSET=utf8 auto_increment=100 comment = '用户信息表';
