package cn.siyue.platform.weixin.client.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponClientService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinCardCouponClientService;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.code.GetCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.consumecode.ConsumeCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.unavailablecardcode.UnavailableCardCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.code.CodeResult;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.GetCardCouponRequest;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.service.CardService;
import com.siyueli.platform.member.common.PageResponse;
import com.siyueli.platform.member.request.coupon.GetCouponListRequest;
import com.siyueli.platform.member.request.coupon.GetCouponRequest;
import com.siyueli.platform.member.response.coupon.CouponResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "微信_卡券_具体卡券接口")
@RestController
@RequestMapping("/coupon")
public class CouponController {


    @Autowired
    private WeixinCardCouponClientService weixinCardCouponClientService;

    @Autowired
    private CouponClientService couponClientService;

    @Autowired
    private CardService cardService;

    @ApiOperation(nickname = "getCouponList", value = "具体卡券-获取用户已领取卡券")
    @LogAnnotation
    @GetMapping("/getCouponList")
    public ResponseData<PageResponse<CouponResponse>> getCouponList(GetCouponListRequest requestParam) {
        ResponseData<PageResponse<CouponResponse>> resp = (ResponseData<PageResponse<CouponResponse>>)couponClientService.getCouponList(requestParam);
        return resp;
    }


    @ApiOperation(nickname = "consumeCardCoupon", value = "具体卡券-核销卡券")
    @LogAnnotation
    @PostMapping("/consumeCardCoupon")
    public ResponseData consumeCardCoupon(@RequestParam("id") Long id) {
        GetCouponRequest getCouponReq = new GetCouponRequest();
        getCouponReq.setId(id);
        ResponseData<CouponResponse> couponRespData = couponClientService.getCoupon(getCouponReq);
        if (ResponseUtil.isSuccess(couponRespData)) {
            CouponResponse coupon = (CouponResponse)couponRespData.getData();

            if (coupon != null) {
                GetCardCouponRequest getCardReq = new GetCardCouponRequest();
                getCardReq.setId(coupon.getCardCouponId());
                ResponseData<GetCardCouponResponse> cardRespData = weixinCardCouponClientService.get(getCardReq);
                if (ResponseUtil.isSuccess(cardRespData)) {
                    GetCardCouponResponse weixinCardCoupon = (GetCardCouponResponse) cardRespData.getData();
                    if (weixinCardCoupon != null) {
                        GetCodeReq req = new GetCodeReq();
                        req.setCard_id(weixinCardCoupon.getCardId());
                        req.setCode(coupon.getCode());
                        req.setCheck_consume(true);
                        try {
                            CodeResult codeResult = cardService.getCode(req);

                            // 本地优惠券更新为核销
                            //couponClientService.updateCouponStatus(id, WeixinConstant.CouponConstant.STATUS_ID_CONSUME);

                            // 微信更新为核销
                            ConsumeCodeReq consumeCodeReq = new ConsumeCodeReq();
                            consumeCodeReq.setCard_id(weixinCardCoupon.getCardId());
                            consumeCodeReq.setCode(coupon.getCode());
                            cardService.consumeCode(consumeCodeReq);

                            return ResponseUtil.success();
                        } catch (WxErrorException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "getCoupon", value = "具体卡券-得到卡券")
    @LogAnnotation
    @GetMapping("/getCoupon")
    public ResponseData<CouponResponse> getCoupon(@RequestParam("id") Long id) {
        GetCouponRequest req = new GetCouponRequest();
        req.setId(id);
        return couponClientService.getCoupon(req);
    }





    @ApiOperation(nickname = "unavailableCardCode", value = "具体卡券-设置卡券失效")
    @LogAnnotation
    @PostMapping("/unavailableCardCode")
    public ResponseData unavailableCardCode(Long id, String reason) {
        try {
            GetCouponRequest getCouponRequest = new GetCouponRequest();
            getCouponRequest.setId(id);
            ResponseData<CouponResponse> couponResp = couponClientService.getCoupon(getCouponRequest);
            if (ResponseUtil.isSuccess(couponResp)) {
                CouponResponse coupon = (CouponResponse)couponResp.getData();
                if (coupon != null) {
                    GetCardCouponRequest getCardCouponRequest = new GetCardCouponRequest();
                    getCardCouponRequest.setId(coupon.getCardCouponId());
                    ResponseData<GetCardCouponResponse> wccResp = weixinCardCouponClientService.get(getCardCouponRequest);
                    if (ResponseUtil.isSuccess(wccResp)) {
                        GetCardCouponResponse weixinCardCoupon = (GetCardCouponResponse)wccResp.getData();
                        if (weixinCardCoupon != null) {
                            // 设置本地优惠券失效
                            couponClientService.updateCouponStatus(id, WeixinConstant.CouponConstant.STATUS_ID_INVALIDATE);

                            UnavailableCardCodeReq requestParam = new UnavailableCardCodeReq();
                            requestParam.setCode(coupon.getCode());
                            requestParam.setCard_id(weixinCardCoupon.getCardId());
                            requestParam.setReason(reason);
                            cardService.unavailableCardCode(requestParam);
                            return ResponseUtil.success();
                        }
                    }

                }
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }


}
