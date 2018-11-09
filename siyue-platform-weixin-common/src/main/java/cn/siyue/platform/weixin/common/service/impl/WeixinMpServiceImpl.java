package cn.siyue.platform.weixin.common.service.impl;

import cn.siyue.platform.weixin.common.config.WeixinConfig;
import cn.siyue.platform.weixin.common.config.WxMpInRedisClusterConfigStorage;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
public class WeixinMpServiceImpl implements WeixinMpService {

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private JedisCluster jedisCluster;

    private WxMpService wxMpService;

    @Override
    public synchronized WxMpService getWxMpService() {
        if (wxMpService == null) {
            WxMpInRedisClusterConfigStorage config = new WxMpInRedisClusterConfigStorage(jedisCluster);
            config.setAppId(weixinConfig.getAppId()); // 设置微信公众号的appid
            config.setSecret(weixinConfig.getSecret()); // 设置微信公众号的app corpSecret
            config.setToken(weixinConfig.getToken()); // 设置微信公众号的token
            config.setAesKey(weixinConfig.getAesKey()); // 设置微信公众号的EncodingAESKey

            wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(config);
        }
        return wxMpService;
    }




}
