package cn.siyue.platform.weixin.client.service.impl;

import cn.siyue.platform.weixin.client.constant.WeixinClientConstant;
import cn.siyue.platform.weixin.client.service.BaseRedisService;
import cn.siyue.platform.weixin.client.service.MenuRedisService;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MenuRedisServiceImpl extends BaseRedisServiceImpl implements MenuRedisService, BaseRedisService {

    private static final String REDIS_KEY_MENU_LIST = WeixinClientConstant.REDIS_KEY_PREFIX + "menuList";

    @Override
    public void saveMenuList(List<WeixinMenuOnlineVo> list) {
        if (list != null && list.size() > 0) {
            try {
                setAndExpireObject(REDIS_KEY_MENU_LIST, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<WeixinMenuOnlineVo> getMenuList() {
        try {
            List<WeixinMenuOnlineVo> list = getList(REDIS_KEY_MENU_LIST, WeixinMenuOnlineVo.class);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            checkTtl(REDIS_KEY_MENU_LIST);
        }
        return null;
    }

    @Override
    public void delMenuList() {
        jedisCluster.del(REDIS_KEY_MENU_LIST);
    }
}
