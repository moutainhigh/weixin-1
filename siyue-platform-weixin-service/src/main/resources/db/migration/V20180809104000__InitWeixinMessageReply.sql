CREATE TABLE weixin_message_reply(
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`reply_type` smallint(2) NOT NULL COMMENT '回复类型',
	`match_type` smallint(2) NOT NULL COMMENT '匹配类型',
	`keyword` varchar(191) NOT NULL COMMENT '关键词',
	`content` text NOT NULL COMMENT '回复内容',
	`status` tinyint(1) NOT NULL COMMENT '状态',
	create_at datetime NOT NULL COMMENT '创建时间',
	`update_at` datetime NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`),
	KEY idx_weixin_message_reply_reply_type (`reply_type`),
	KEY `idx_weixin_message_reply_match_type` (`match_type`),
	KEY `idx_weixin_message_reply_status` (`status`),
	KEY `idx_weixin_message_reply_keyword` (`keyword`)
) COMMENT='微信消息回复表';