ALTER TABLE `weixin_card_coupon`
  ADD COLUMN `cover_material_url` varchar(255) NULL COMMENT '封面素材url' AFTER `cover_image_secret`;