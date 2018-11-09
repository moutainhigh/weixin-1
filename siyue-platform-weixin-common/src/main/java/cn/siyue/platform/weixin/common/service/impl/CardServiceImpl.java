package cn.siyue.platform.weixin.common.service.impl;

import cn.siyue.platform.util.JsonUtil;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.consumecode.ConsumeCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.*;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.CreateCardQrcodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.deletecard.DeleteCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getusercardlist.GetCardListReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.batchget.BatchGetReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.code.GetCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getcard.GetCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.modifystock.ModifyStockReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.batchget.BatchGetResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.code.CodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.consumecode.ConsumeCodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.createcard.CardInfoResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.createcardqrcode.CreateCardQrcodeResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.result.getusercardlist.GetCardListResult;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.setselfconsumecell.SetSelfConsumeCellReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.settestwhitelist.SetTestWhiteListReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.unavailablecardcode.UnavailableCardCodeReq;
import cn.siyue.platform.weixin.common.response.coupon.ColorResponse;
import cn.siyue.platform.weixin.common.service.CardService;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import com.fasterxml.jackson.databind.JsonNode;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private static final String CARD_CREATE_URL = "https://api.weixin.qq.com/card/create";

    private static final String CARD_CODE_GET_URL = "https://api.weixin.qq.com/card/code/get";

    private static final String CARD_GET_URL = "https://api.weixin.qq.com/card/get";

    private static final String CARD_BATCHGET_URL = "https://api.weixin.qq.com/card/batchget";

    private static final String CARD_USER_GETCARDLIST_URL = "https://api.weixin.qq.com/card/user/getcardlist";

    private static final String CARD_QRCODE_CREATE_URL = "https://api.weixin.qq.com/card/qrcode/create";

    private static final String CARD_TESTWHITELIST_SET_URL = "https://api.weixin.qq.com/card/testwhitelist/set";

    private static final String CARD_SELFCONSUMECELL_SET_URL = "https://api.weixin.qq.com/card/selfconsumecell/set";

    private static final String CARD_CODE_CONSUME_URL = "https://api.weixin.qq.com/card/code/consume";

    private static final String CARD_MODIFY_STOCK_URL = "https://api.weixin.qq.com/card/modifystock";

    private static final String CARD_DELETE_URL = "https://api.weixin.qq.com/card/delete";

    private static final String CARD_CODE_UNAVAILABLE = "https://api.weixin.qq.com/card/code/unavailable";




    @Autowired
    private WeixinMpService weixinMpService;

    @Override
    public WxMediaImgUploadResult uploadImg(File file) {
        try {
            WxMediaImgUploadResult result = weixinMpService.getWxMpService().getMaterialService().mediaImgUpload(file);
            return result;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CardInfoResult create(CreateCardReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_CREATE_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                return WxMpGsonBuilder.create().fromJson(responseText, CardInfoResult.class);
            } else {
                throw new WxErrorException(wxError);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public CodeResult getCode(GetCodeReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_CODE_GET_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                return WxMpGsonBuilder.create().fromJson(responseText, CodeResult.class);
            } else {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Card getCardType(JsonNode cardNode) throws IOException {
        if (!JsonUtil.isNullNode(cardNode)) {
            JsonNode cardTypeNode = cardNode.path("card_type");
            if (!JsonUtil.isNullNode(cardTypeNode)) {
                String cardType = cardTypeNode.asText();
                if (WeixinConstant.CardTypeConstant.CARD_TYPE_CASH.equals(cardType)) {
                    CashCard cashCard = new CashCard();
                    cashCard.setCard_type(cardType);
                    JsonNode cardInfoNode = cardNode.path(cardType.toLowerCase());
                    CashCardInfoDto cashCardInfoDto = JsonUtil.jsonToObject(cardInfoNode, CashCardInfoDto.class);
                    cashCard.setCash(cashCardInfoDto);
                    return cashCard;
                } else if (WeixinConstant.CardTypeConstant.CARD_TYPE_DISCOUNT.equals(cardType)) {
                    DiscountCard discountCard = new DiscountCard();
                    discountCard.setCard_type(cardType);
                    JsonNode cardInfoNode = cardNode.path(cardType.toLowerCase());
                    DiscountCardInfoDto discountCardInfoDto = JsonUtil.jsonToObject(cardInfoNode, DiscountCardInfoDto.class);
                    discountCard.setDiscount(discountCardInfoDto);
                    return discountCard;
                } else if (WeixinConstant.CardTypeConstant.CARD_TYPE_GIFT.equals(cardType)) {
                    GiftCard giftCard = new GiftCard();
                    giftCard.setCard_type(cardType);
                    JsonNode cardInfoNode = cardNode.path(cardType.toLowerCase());
                    GiftCardInfoDto giftCardInfoDto = JsonUtil.jsonToObject(cardInfoNode, GiftCardInfoDto.class);
                    giftCard.setGift(giftCardInfoDto);
                    return giftCard;
                } else if (WeixinConstant.CardTypeConstant.CARD_TYPE_MEMBER_CARD.equals(cardType)) {
                    MemberCard memberCard = new MemberCard();
                    memberCard.setCard_type(cardType);
                    JsonNode cardInfoNode = cardNode.path(cardType.toLowerCase());
                    MemberCardInfoDto memberCardInfoDto = JsonUtil.jsonToObject(cardInfoNode, MemberCardInfoDto.class);
                    memberCard.setMember_card(memberCardInfoDto);
                    return memberCard;
                }

            }
        }
        return null;
    }

    @Override
    public Card getCard(GetCardReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_GET_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                JsonNode result = JsonUtil.parseJson(responseText);
                JsonNode cardNode = result.path("card");
                return getCardType(cardNode);
            }
            throw new WxErrorException(wxError);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BatchGetResult batchGet(BatchGetReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_BATCHGET_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                return WxMpGsonBuilder.create().fromJson(responseText, BatchGetResult.class);
            } else {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GetCardListResult getUserCardList(GetCardListReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_USER_GETCARDLIST_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                return WxMpGsonBuilder.create().fromJson(responseText, GetCardListResult.class);
            } else {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WxMpMassSendResult sendByOpenId(List<String> openIds, String cardId) {
        try {
            WxMpMassOpenIdsMessage msg = new WxMpMassOpenIdsMessage();
            if (openIds != null && openIds.size() > 0) {
                for (String openId : openIds) {
                    msg.addUser(openId);
                }
            }
            msg.setMsgType("wxcard");
            WxMpMassSendResult content = weixinMpService.getWxMpService().getMassMessageService().massOpenIdsMessageSend(msg);
            return content;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CreateCardQrcodeResult createCardQrcode(CreateCardQrcodeReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_QRCODE_CREATE_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                return WxMpGsonBuilder.create().fromJson(responseText, CreateCardQrcodeResult.class);
            } else {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String setTestWhiteList(SetTestWhiteListReq req) {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String respStr = weixinMpService.getWxMpService().post(CARD_TESTWHITELIST_SET_URL, content);
            System.out.println(respStr);
            return respStr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ColorResponse> getColorList() {
        String[][] colorArr = {
                {"Color010", "#63b359"},
                {"Color020", "#2c9f67"},
                {"Color030", "#509fc9"},
                {"Color040", "#5885cf"},
                {"Color050", "#9062c0"},
                {"Color060", "#d09a45"},
                {"Color070", "#e4b138"},
                {"Color080", "#ee903c"},
                {"Color081", "#f08500"},
                {"Color082", "#a9d92d"},
                {"Color090", "#dd6549"},
                {"Color100", "#cc463d"},
                {"Color101", "#cf3e36"},
                {"Color102", "#5E6671"}
        };

        List<ColorResponse> list = new ArrayList<ColorResponse>();
        for (int i = 0; i < colorArr.length; i++) {
            ColorResponse color = new ColorResponse();
            color.setColorName(colorArr[i][0]);
            color.setColorValue(colorArr[i][1]);
            list.add(color);
        }
        return list;
    }

    @Override
    public String setSelfConsumeCell(SetSelfConsumeCellReq req) {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String respStr = weixinMpService.getWxMpService().post(CARD_SELFCONSUMECELL_SET_URL, content);
            System.out.println(respStr);
            return respStr;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public ConsumeCodeResult consumeCode(ConsumeCodeReq req) throws WxErrorException {

        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_CODE_CONSUME_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() == 0) {
                return WxMpGsonBuilder.create().fromJson(responseText, ConsumeCodeResult.class);
            } else {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modifyStock(ModifyStockReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_MODIFY_STOCK_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() != 0) {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCard(DeleteCardReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_DELETE_URL, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() != 0) {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unavailableCardCode(UnavailableCardCodeReq req) throws WxErrorException {
        try {
            String content = JsonUtil.toJsonString(req);
            System.out.println(content);
            String responseText = weixinMpService.getWxMpService().post(CARD_CODE_UNAVAILABLE, content);
            System.out.println(responseText);
            WxError wxError = WxError.fromJson(responseText, WxType.MP);
            if (wxError.getErrorCode() != 0) {
                throw new WxErrorException(wxError);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
