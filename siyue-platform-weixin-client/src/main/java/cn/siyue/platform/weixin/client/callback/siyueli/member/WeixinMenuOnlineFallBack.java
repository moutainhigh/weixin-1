package cn.siyue.platform.weixin.client.callback.siyueli.member;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.BaseServiceFallBack;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMenuOnlineClientService;
import cn.siyue.platform.weixin.common.common.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class WeixinMenuOnlineFallBack extends BaseServiceFallBack implements WeixinMenuOnlineClientService {
    @Override
    public ResponseData getList(PageRequest pageRequest) {
        return getDownGradeResponse();
    }
}
