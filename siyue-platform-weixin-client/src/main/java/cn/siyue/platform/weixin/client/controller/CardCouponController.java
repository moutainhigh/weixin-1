package cn.siyue.platform.weixin.client.controller;


import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.client.service.CouponService;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponTaskClientService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinCardCouponClientService;
import cn.siyue.platform.weixin.client.util.HttpDownloadUtil;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.*;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.ActionInfoDto;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.CardDto;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.CreateCardQrcodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.deletecard.DeleteCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getcard.GetCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.modifystock.ModifyStockReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.createcard.CardInfoResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.createcardqrcode.CreateCardQrcodeResult;
import cn.siyue.platform.weixin.common.entity.WeixinCardCoupon;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.*;
import cn.siyue.platform.weixin.common.response.coupon.ColorResponse;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.SearchCardCouponResponse;
import cn.siyue.platform.weixin.common.service.CardService;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.security.action.GetPropertyAction;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.List;

@Api(tags = "微信_卡券_本地卡券定义接口")
@RestController
@RequestMapping("/cardCoupon")
public class CardCouponController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardCouponController.class);

    @Autowired
    private WeixinCardCouponClientService weixinCardCouponClientService;

    @Autowired
    private CouponTaskClientService couponTaskClientService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CouponService couponService;



    @ApiOperation(nickname = "add", value = "微信本地卡券定义-新增卡券")
    @LogAnnotation
    @PostMapping("/add")
    public ResponseData add(@Valid @RequestBody AddCardCouponWebRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            AddCardCouponRequest addCardCouponRequest = new AddCardCouponRequest();
            BeanUtils.copyProperties(requestParam, addCardCouponRequest);
            Integer taskTypeId = getTaskTypeId(requestParam.getTaskId());
            if (taskTypeId != null) {
                addCardCouponRequest.setTaskTypeId(taskTypeId);
                return weixinCardCouponClientService.add(addCardCouponRequest);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        return ResponseUtil.fail();
    }

    private Integer getTaskTypeId(Long taskId) {
        ResponseData<GetCouponTaskResponse> couponTaskRespData = couponTaskClientService.getCouponTask(taskId);
        if (ResponseUtil.isSuccess(couponTaskRespData)) {
            GetCouponTaskResponse couponTask = (GetCouponTaskResponse)couponTaskRespData.getData();
            if (couponTask != null) {
                return couponTask.getTypeId();
            }
        }
        return null;
    }


    @ApiOperation(nickname = "update", value = "微信本地卡券定义-更新卡券")
    @LogAnnotation
    @PostMapping("/update")
    public ResponseData update(@Valid @RequestBody UpdateCardCouponWebRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            UpdateCardCouponRequest updateCardCouponRequest = new UpdateCardCouponRequest();
            BeanUtils.copyProperties(requestParam, updateCardCouponRequest);
            Integer taskTypeId = getTaskTypeId(requestParam.getTaskId());
            if (taskTypeId != null) {
                updateCardCouponRequest.setTaskTypeId(taskTypeId);
                return weixinCardCouponClientService.update(updateCardCouponRequest);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        return ResponseUtil.fail();
    }





    @ApiOperation(nickname = "get", value = "微信本地卡券定义-得到卡券")
    @LogAnnotation
    @GetMapping("/get")
    public ResponseData<GetCardCouponResponse> get(@Valid GetCardCouponWebRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            GetCardCouponRequest getCardCouponRequest = new GetCardCouponRequest();
            getCardCouponRequest.setId(requestParam.getId());
            return weixinCardCouponClientService.get(getCardCouponRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "getList", value = "微信本地卡券定义-得到卡券列表")
    @LogAnnotation
    @GetMapping("/getList")
    public ResponseData<PageResponse<GetCardCouponResponse>> getList(@Valid GetCardCouponListRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            return weixinCardCouponClientService.getList(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "search", value = "微信本地卡券定义-搜索卡券")
    @LogAnnotation
    @GetMapping("/search")
    public ResponseData<PageResponse<SearchCardCouponResponse>> search(@Valid SearchCardCouponRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }
        try {
            return weixinCardCouponClientService.search(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "getColorList", value = "得到卡券颜色列表")
    @LogAnnotation
    @GetMapping("/getColorList")
    public ResponseData<List<ColorResponse>> getColorList() {
        try {
            List<ColorResponse> list = cardService.getColorList();
            return ResponseUtil.success(list);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "publish", value = "一类卡券-发布卡券")
    @LogAnnotation
    @PostMapping("/publish")
    public ResponseData publish(@Valid @RequestBody PublishCouponRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            GetCardCouponRequest getCardCouponRequest = new GetCardCouponRequest();
            getCardCouponRequest.setId(requestParam.getId());
            ResponseData<GetCardCouponResponse> cardCouponRespData = weixinCardCouponClientService.get(getCardCouponRequest);


            if (cardCouponRespData != null && ResponseBackCode.SUCCESS.getValue() == cardCouponRespData.getCode()) {
                GetCardCouponResponse cardCoupon = (GetCardCouponResponse)cardCouponRespData.getData();

                ResponseData<GetCouponTaskResponse> couponTaskRespData = couponTaskClientService.getCouponTask(cardCoupon.getTaskId());
                if (couponTaskRespData != null && ResponseBackCode.SUCCESS.getValue() == couponTaskRespData.getCode()) {
                    if (StringUtils.isEmpty(cardCoupon.getCoverMaterialUrl())) {
                        // 上传素材
                        WxMediaImgUploadResult img = uploadImg(cardCoupon);
                        cardCoupon.setCoverMaterialUrl(img.getUrl());
                    }

                    GetCouponTaskResponse couponTask = (GetCouponTaskResponse)couponTaskRespData.getData();
                    CreateCardReq createCardReq = couponService.getCardReq(cardCoupon, null, couponTask);
                    CardInfoResult cardInfoResult = cardService.create(createCardReq);
                    PublishCardRequest publishCardRequest = new PublishCardRequest();
                    publishCardRequest.setId(cardCoupon.getId());
                    publishCardRequest.setCardId(cardInfoResult.getCard_id());
                    publishCardRequest.setCoverMaterialUrl(cardCoupon.getCoverMaterialUrl());
                    weixinCardCouponClientService.publish(publishCardRequest);
                    return ResponseUtil.success();
                }
            } else {
                return ResponseUtil.fail();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
        return ResponseUtil.fail();
    }

    private WxMediaImgUploadResult uploadImg(GetCardCouponResponse cardCoupon) {
        File file = null;
        BufferedOutputStream bof = null;
        FileOutputStream fos = null;
        if (StringUtils.isNotEmpty(cardCoupon.getCoverImageUrl())) {
            try {
                byte[] data = HttpDownloadUtil.download(cardCoupon.getCoverImageUrl() + "!" + cardCoupon.getCoverImageSecret());
                int suffixPointIndex = cardCoupon.getCoverImageUrl().lastIndexOf(".");
                String suffix = cardCoupon.getCoverImageUrl().substring(suffixPointIndex);

                File tmpdir = new File(AccessController
                        .doPrivileged(new GetPropertyAction("java.io.tmpdir")));

                File imgDir = new File(tmpdir + File.separator + "weixin_img");
                if (!imgDir.exists()) {
                    imgDir.mkdir();
                }

                file = File.createTempFile("weixin_card_coupon_" + cardCoupon.getId() + "_", suffix, imgDir);

                fos = new FileOutputStream(file);
                bof = new BufferedOutputStream(fos);
                bof.write(data);
                bof.flush();

                WxMediaImgUploadResult result = cardService.uploadImg(file);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bof != null) {
                    try {
                        bof.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (file != null) {
                    file.deleteOnExit();
                }
            }

        }
        return null;
    }

    @ApiOperation(nickname = "deleteCard", value = "一类卡券-删除卡券")
    @LogAnnotation
    @PostMapping("/deleteCard")
    public ResponseData deleteCard(@Valid DeleteCardRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            GetCardCouponRequest getCardCouponRequest = new GetCardCouponRequest();
            getCardCouponRequest.setId(requestParam.getId());
            ResponseData<GetCardCouponResponse> wccResp = weixinCardCouponClientService.get(getCardCouponRequest);
            if (ResponseUtil.isSuccess(wccResp)) {
                GetCardCouponResponse weixinCardCoupon = (GetCardCouponResponse)wccResp.getData();
                if (weixinCardCoupon != null) {
                    ResponseData resp = weixinCardCouponClientService.updateStatus(requestParam.getId(), WeixinConstant.CardCouponConstant.STATUS_CARD_DEL);
                    if (ResponseUtil.isSuccess(resp)) {
                        if (StringUtils.isNotEmpty(weixinCardCoupon.getCardId())) {
                            DeleteCardReq delCardReq = new DeleteCardReq();
                            delCardReq.setCard_id(weixinCardCoupon.getCardId());
                            cardService.deleteCard(delCardReq);
                        }

                    }
                    return ResponseUtil.success();
                }
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "modifyStock", value = "一类卡券-修改库存")
    @LogAnnotation
    @PostMapping("/modifyStock")
    public ResponseData modifyStock(@Valid ModifyStockRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        Long id = requestParam.getId();
        Integer stockQty = requestParam.getStockQty();

        try {
            GetCardCouponRequest req = new GetCardCouponRequest();
            req.setId(requestParam.getId());
            ResponseData<GetCardCouponResponse> resp = weixinCardCouponClientService.get(req);
            if (ResponseUtil.isSuccess(resp)) {
                GetCardCouponResponse weixinCardCoupon = (GetCardCouponResponse)resp.getData();
                if (weixinCardCoupon != null) {
                    weixinCardCouponClientService.updateStock(id, stockQty);
                    ModifyStockReq modStockReq = new ModifyStockReq();
                    if (StringUtils.isNotEmpty(weixinCardCoupon.getCardId())) {
                        modStockReq.setCard_id(weixinCardCoupon.getCardId());
                        GetCardReq getCardReq = new GetCardReq();
                        getCardReq.setCard_id(weixinCardCoupon.getCardId());
                        Card card = cardService.getCard(getCardReq);
                        CardInfoDto cardInfoDto = getCardInfo(card);
                        if (cardInfoDto != null && cardInfoDto.getBase_info() != null && cardInfoDto.getBase_info().getSku() != null) {
                            long qty = cardInfoDto.getBase_info().getSku().getQuantity();
                            if (stockQty > qty) {
                                modStockReq.setIncrease_stock_value(new Long(stockQty - qty).intValue());
                                cardService.modifyStock(modStockReq);
                            } else if (stockQty < qty) {
                                modStockReq.setReduce_stock_value(new Long(qty-stockQty).intValue());
                                cardService.modifyStock(modStockReq);
                            }

                        }


                    }
                    return ResponseUtil.success();
                }
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail();
    }

    private CardInfoDto getCardInfo(Card card) {
        if (card != null) {
            if (card instanceof CashCard) {
                return ((CashCard) card).getCash();
            } else if (card instanceof DiscountCard) {
                return ((DiscountCard) card).getDiscount();
            } else if (card instanceof GiftCard) {
                return ((GiftCard) card).getGift();
            } else if (card instanceof MemberCard) {
                return ((MemberCard) card).getMember_card();
            }
        }
        return null;
    }

    @ApiOperation(nickname = "sendCardByQrcode", value = "一类卡券-通过二维码投放卡券")
    @LogAnnotation
    @GetMapping("/sendCardByQrcode")
    public ResponseData sendCardByQrcode(@Valid SendCardByQrcodeRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        try {
            Long id = requestParam.getId();
            String openId = requestParam.getOpenId();
            CreateCardQrcodeReq req = new CreateCardQrcodeReq();
            req.setAction_name("QR_CARD");
            req.setExpire_seconds(1800);
            ActionInfoDto actionInfo = new ActionInfoDto();
            req.setAction_info(actionInfo);
            CardDto card = new CardDto();
            actionInfo.setCard(card);
            GetCardCouponRequest getCardReq = new GetCardCouponRequest();
            getCardReq.setId(id);
            ResponseData<GetCardCouponResponse> cardRespData = weixinCardCouponClientService.get(getCardReq);
            if (ResponseUtil.isSuccess(cardRespData)) {
                GetCardCouponResponse weixinCardCoupon = (GetCardCouponResponse)cardRespData.getData();
                if (weixinCardCoupon != null) {
                    card.setCard_id(weixinCardCoupon.getCardId());
                    card.setOpenid(openId);
                    card.setIs_unique_code(false);
                    card.setOuter_str("12b");
                    CreateCardQrcodeResult cardResult = cardService.createCardQrcode(req);
                    return ResponseUtil.success(cardResult);
                }
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        return ResponseUtil.fail();
    }


}
