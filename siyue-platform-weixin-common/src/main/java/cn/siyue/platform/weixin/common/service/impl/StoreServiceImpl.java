package cn.siyue.platform.weixin.common.service.impl;

import cn.siyue.platform.weixin.common.service.StoreService;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import me.chanjar.weixin.mp.api.WxMpStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private WeixinMpService weixinMpService;


    @Override
    public WxMpStoreService getWxMpStoreService() {
        return weixinMpService.getWxMpService().getStoreService();
    }
}
