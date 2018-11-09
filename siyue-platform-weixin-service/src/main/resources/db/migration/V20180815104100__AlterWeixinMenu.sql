ALTER TABLE `weixin_menu`
ADD UNIQUE `uidx_weixin_menu_name_parent_id` (`name`, `parent_id`);