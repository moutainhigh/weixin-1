ALTER TABLE `weixin_message_reply`
MODIFY COLUMN `content` text NULL COMMENT '回复内容';

ALTER TABLE `weixin_message_reply`
ADD COLUMN `title` varchar(255) NULL COMMENT '素材标题' AFTER `media_id`;