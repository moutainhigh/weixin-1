package cn.siyue.platform.weixin.client.service.weixin;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.siyueli.member.WeixinMenuOnlineFallBack;
import cn.siyue.platform.weixin.common.common.PageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "weixin-service", fallback = WeixinMenuOnlineFallBack.class)
public interface WeixinMenuOnlineClientService {

    @RequestMapping(value = "/weixinMenuOnline/getList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData getList(PageRequest pageRequest);
}
