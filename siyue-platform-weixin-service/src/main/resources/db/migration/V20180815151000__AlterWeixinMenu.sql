ALTER TABLE `weixin_menu`
ADD COLUMN `reply_type` smallint(2) NULL COMMENT '回复类型' AFTER `media_id`;