package cn.siyue.platform.weixin.client.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;

import java.util.List;

public interface MessageService {

    public WxMpMessageRouter createWxMpMessageRouter(WxMpService wxMpService, List<WeixinMessageReplyResponse> list, List<WeixinMenuOnlineVo> menuList);
}
