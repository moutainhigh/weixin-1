ALTER TABLE `weixin_card_coupon`
ADD COLUMN `stock_qty` int(11) NOT NULL COMMENT '库存数量' AFTER `card_id`;