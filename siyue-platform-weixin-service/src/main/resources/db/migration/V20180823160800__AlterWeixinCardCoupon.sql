ALTER TABLE `weixin_card_coupon`
ADD COLUMN `card_id` varchar(128) NULL COMMENT '微信卡券id' AFTER `share_give`;