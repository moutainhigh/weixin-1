ALTER TABLE weixin_message_reply
ADD COLUMN `type` SMALLINT(2) NOT NULL COMMENT '类型：1-关注回复，2-无关键词回复，3-关键词回复';

ALTER TABLE weixin_message_reply
MODIFY COLUMN `keyword` varchar(191) NULL COMMENT '关键词';

ALTER TABLE weixin_message_reply
ADD INDEX weixin_message_reply_type (`type`);