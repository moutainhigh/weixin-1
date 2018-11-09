ALTER TABLE `weixin_card_coupon`
  MODIFY COLUMN `task_id` bigint(20) NULL COMMENT '卡券任务id';

ALTER TABLE `weixin_card_coupon`
  ADD COLUMN `refuse_reason` varchar(255) NULL COMMENT '审核不通过原因' AFTER `status`;

ALTER TABLE `weixin_card_coupon`
  ADD COLUMN `ext` text NULL COMMENT '扩展字段' AFTER `refuse_reason`;