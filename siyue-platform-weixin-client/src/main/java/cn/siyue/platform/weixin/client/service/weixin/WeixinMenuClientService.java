package cn.siyue.platform.weixin.client.service.weixin;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.WeixinMenuFallBack;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.request.weixinmenu.*;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "weixin-service", fallback = WeixinMenuFallBack.class)
public interface WeixinMenuClientService {

    @RequestMapping(value = "/weixinMenu/addWeixinMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData addWeixinMenu(@RequestBody AddMenuRequest requestParam);

    @RequestMapping(value = "/weixinMenu/updateWeixinMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData updateWeixinMenu(@RequestBody UpdateMenuRequest requestParam);

    @RequestMapping(value = "/weixinMenu/deleteWeixinMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData deleteWeixinMenu(@RequestBody DeleteMenuRequest requestParam);

    @RequestMapping(value = "/weixinMenu/getWeixinMenuList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<PageResponse<GetWeixinMenuListResponse>> getWeixinMenuList(@RequestBody PageRequest requestParam);

    @RequestMapping(value = "/weixinMenu/syncMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData syncMenu(SyncMenuRequest requestParam);

    @RequestMapping(value = "/weixinMenu/moveUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData moveUp(@RequestBody MoveUpRequest requestParam);

    @RequestMapping(value = "/weixinMenu/moveDown", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData moveDown(@RequestBody MoveDownRequest requestParam);

    @RequestMapping(value = "/weixinMenu/publish", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData publish();
}
