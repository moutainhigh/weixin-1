package cn.siyue.platform.weixin.client.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponClientService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinCardCouponClientService;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.batchget.BatchGetReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.code.GetCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.consumecode.ConsumeCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.Card;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.deletecard.DeleteCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getcard.GetCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getusercardlist.GetCardListReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.modifystock.ModifyStockReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.unavailablecardcode.UnavailableCardCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.batchget.BatchGetResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.code.CodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.getusercardlist.GetCardListResult;
import cn.siyue.platform.weixin.common.service.CardService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "微信_卡券_直接与微信交互的卡券接口")
@RestController
@RequestMapping("/weixinCoupon")
public class WeixinCouponController {


    @Autowired
    private WeixinCardCouponClientService weixinCardCouponClientService;

    @Autowired
    private CouponClientService couponClientService;

    @Autowired
    private CardService cardService;


    @ApiOperation(nickname = "consumeCardCoupon", value = "具体卡券-核销卡券")
    @LogAnnotation
    @PostMapping("/consumeCardCoupon")
    public ResponseData consumeCardCoupon(@RequestBody ConsumeCodeReq requestParam) {
        GetCodeReq req = new GetCodeReq();
        req.setCard_id(requestParam.getCard_id());
        req.setCode(requestParam.getCode());
        req.setCheck_consume(true);
        try {
            CodeResult codeResult = cardService.getCode(req);
            cardService.consumeCode(requestParam);

            return ResponseUtil.success();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "getCardCode", value = "具体卡券-查询Code")
    @LogAnnotation
    @GetMapping("/getCardCode")
    public ResponseData getCardCode(@RequestParam String code) {
        GetCodeReq req = new GetCodeReq();
        req.setCode(code);
        try {
            CodeResult codeResult = cardService.getCode(req);
            return ResponseUtil.success(codeResult);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "getUserCardList", value = "具体卡券-获取用户已领取卡券")
    @LogAnnotation
    @GetMapping("/getUserCardList")
    public ResponseData getUserCardList(String openid, String card_id) {
        GetCardListReq req = new GetCardListReq();
        req.setCard_id(card_id);
        req.setOpenid(openid);
        GetCardListResult result = null;
        try {
            result = cardService.getUserCardList(req);
            return ResponseUtil.success(result);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "getCardDetail", value = "一类卡券-查看卡券详情")
    @LogAnnotation
    @GetMapping("/getCardDetail")
    public ResponseData getCardDetail(String card_id) {
        GetCardReq req = new GetCardReq();
        req.setCard_id(card_id);
        try {
            Card result = cardService.getCard(req);
            return ResponseUtil.success(result);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "batchGetCard", value = "一类卡券-批量查询卡券列表")
    @LogAnnotation
    @PostMapping("/batchGetCard")
    public ResponseData batchGetCard(@RequestBody BatchGetReq requestParam) {
        try {
            BatchGetResult result = cardService.batchGet(requestParam);
            return ResponseUtil.success(result);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "modifyStock", value = "一类卡券-修改库存")
    @LogAnnotation
    @PostMapping("/modifyStock")
    public ResponseData modifyStock(@RequestBody ModifyStockReq requestParam) {
        try {
            cardService.modifyStock(requestParam);
            return ResponseUtil.success();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "deleteCard", value = "一类卡券-删除卡券")
    @LogAnnotation
    @PostMapping("/deleteCard")
    public ResponseData deleteCard(@RequestBody DeleteCardReq requestParam) {
        try {
            cardService.deleteCard(requestParam);
            return ResponseUtil.success();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "unavailableCardCode", value = "具体卡券-设置卡券失效")
    @LogAnnotation
    @PostMapping("/unavailableCardCode")
    public ResponseData unavailableCardCode(@RequestBody UnavailableCardCodeReq requestParam) {
        try {
            cardService.unavailableCardCode(requestParam);
            return ResponseUtil.success();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }


}
