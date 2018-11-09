ALTER TABLE weixin_message_reply
ADD COLUMN `media_id` varchar(128) NULL COMMENT '多媒体id' AFTER `content`;