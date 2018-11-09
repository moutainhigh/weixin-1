ALTER TABLE `weixin_card_coupon`
  ADD COLUMN `time_limit` text NULL COMMENT '使用时间限制，json格式' AFTER `use_notice`;