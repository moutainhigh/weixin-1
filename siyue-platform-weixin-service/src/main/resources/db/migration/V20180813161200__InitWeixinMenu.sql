CREATE TABLE `weixin_menu` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`type` varchar(64) NOT NULL COMMENT '类型',
	`name` varchar(64) NOT NULL COMMENT '名称',
	`key` varchar(64) NULL COMMENT '菜单KEY值',
	`url` varchar(255) NULL COMMENT '用户点击菜单可打开链接',
	`media_id`  varchar(128) NULL COMMENT '多媒体id',
	`app_id` varchar(128) NULL COMMENT '小程序的appid',
	`page_path` varchar(255) NULL COMMENT '小程序的页面路径',
	`parent_id` bigint(20) NULL COMMENT '父id',
	`title` varchar(255) NULL COMMENT '素材标题',
	`create_at` datetime NOT NULL COMMENT '创建时间',
	`update_at` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
) COMMENT='微信菜单表';