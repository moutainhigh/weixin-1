ALTER TABLE `weixin_menu`
ADD COLUMN `sort` smallint(4) NOT NULL DEFAULT 1 COMMENT '排序' AFTER `title`;