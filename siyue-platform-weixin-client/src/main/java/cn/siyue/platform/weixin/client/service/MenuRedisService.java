package cn.siyue.platform.weixin.client.service;

import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;

import java.util.List;

public interface MenuRedisService {

    public void saveMenuList(List<WeixinMenuOnlineVo> list);

    public List<WeixinMenuOnlineVo> getMenuList();

    public void delMenuList();
}
