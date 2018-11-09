ALTER TABLE `weixin_card_coupon`
ADD COLUMN `status` tinyint(2) NOT NULL COMMENT '状态' AFTER `card_id`;