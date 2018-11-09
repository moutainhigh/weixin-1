package cn.siyue.platform.weixin.client.service.weixin;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.WeixinCardCouponFallBack;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.*;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.SearchCardCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weixin-service", fallback = WeixinCardCouponFallBack.class)
public interface WeixinCardCouponClientService {

    @RequestMapping(value = "/weixinCardCoupon/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData add(AddCardCouponRequest requestParam);

    @RequestMapping(value = "/weixinCardCoupon/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData update(UpdateCardCouponRequest requestParam);

    @RequestMapping(value = "/weixinCardCoupon/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<GetCardCouponResponse> get(GetCardCouponRequest requestParam);

    @RequestMapping(value = "/weixinCardCoupon/getList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<PageResponse<GetCardCouponResponse>> getList(GetCardCouponListRequest requestParam);

    @RequestMapping(value = "/weixinCardCoupon/publish", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData publish(PublishCardRequest requestParam);

    @RequestMapping(value = "/weixinCardCoupon/updateStatus", method = RequestMethod.POST)
    ResponseData updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status);

    @RequestMapping(value = "/weixinCardCoupon/updateStock", method = RequestMethod.POST)
    ResponseData updateStock(@RequestParam("id") Long id, @RequestParam("stockQty") Integer stockQty);

    @RequestMapping(value = "/weixinCardCoupon/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseData<PageResponse<SearchCardCouponResponse>> search(SearchCardCouponRequest requestParam);
}
