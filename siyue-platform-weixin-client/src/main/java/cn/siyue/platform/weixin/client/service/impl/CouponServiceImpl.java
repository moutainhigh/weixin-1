package cn.siyue.platform.weixin.client.service.impl;

import cn.siyue.platform.util.JsonUtil;
import cn.siyue.platform.weixin.client.service.CouponService;
import cn.siyue.platform.weixin.client.util.CurrencyUtil;
import cn.siyue.platform.weixin.common.config.WeixinConfig;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.advancedinfo.*;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Base_info;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Date_info;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Sku;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.*;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.TimeLimitVo;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.siyueli.platform.member.pojo.coupon.Coupon;
import com.siyueli.platform.member.pojo.coupon.CouponTask;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private WeixinConfig weixinConfig;

    @Override
    public CreateCardReq getCardReq(GetCardCouponResponse cardCoupon, Coupon coupon, CouponTask couponTask) {
        return getCardInfo(cardCoupon, coupon, couponTask);
    }

    private CreateCardReq getCardInfo(GetCardCouponResponse cardCoupon, Coupon coupon, CouponTask couponTask) {
        CreateCardReq req = null;

        // CASH
        if (WeixinConstant.CouponTaskConstant.TYPE_ID_CASH.equals(couponTask.getTypeId())) {
            req = new CreateCardReq();
            CashCard card = new CashCard();
            req.setCard(card);
            card.setCard_type("CASH");
            CashCardInfoDto cash = new CashCardInfoDto();
            card.setCash(cash);
            cash.setLeast_cost(CurrencyUtil.yuanToFen(couponTask.getThreshold()).intValue());
            cash.setReduce_cost(CurrencyUtil.yuanToFen(couponTask.getDiscount()).intValue());
            setBaseInfo(cardCoupon, cash, coupon, couponTask);
            setAdvancedInfo(cardCoupon, cash, coupon, couponTask);
        } else if (WeixinConstant.CouponTaskConstant.TYPE_ID_DISCOUNT.equals(couponTask.getTypeId())) {
            req = new CreateCardReq();
            DiscountCard card = new DiscountCard();
            req.setCard(card);
            card.setCard_type("DISCOUNT");
            DiscountCardInfoDto discount = new DiscountCardInfoDto();
            card.setDiscount(discount);
            discount.setDiscount(couponTask.getDiscount().intValue());
            setBaseInfo(cardCoupon, discount, coupon, couponTask);
            setAdvancedInfo(cardCoupon, discount, coupon, couponTask);
        } else if (WeixinConstant.CouponTaskConstant.TYPE_ID_GIFT.equals(couponTask.getTypeId())) {
            req = new CreateCardReq();
            GiftCard card = new GiftCard();
            req.setCard(card);
            card.setCard_type("GIFT");
            GiftCardInfoDto gift = new GiftCardInfoDto();
            gift.setGift("礼品");
            setBaseInfo(cardCoupon, gift, coupon, couponTask);
            setAdvancedInfo(cardCoupon, gift, coupon, couponTask);
        } else if (WeixinConstant.CouponTaskConstant.TYPE_ID_MEMBER_CARD.equals(couponTask.getTypeId())) {
            req = new CreateCardReq();
            MemberCard memberCard = new MemberCard();
            req.setCard(memberCard);
            memberCard.setCard_type(WeixinConstant.CardTypeConstant.CARD_TYPE_MEMBER_CARD);
            MemberCardInfoDto memberCardInfo = new MemberCardInfoDto();
            memberCard.setMember_card(memberCardInfo);

            CreateCardReq createCardExt = getCardExt(cardCoupon.getExt(), couponTask.getTypeId());
            if (createCardExt != null) {
                MemberCard cardExt = (MemberCard)createCardExt.getCard();
                if (cardExt != null) {
                    MemberCardInfoDto memberCardInfoExt = cardExt.getMember_card();
                    if (memberCardInfoExt != null) {
                        BeanUtils.copyProperties(memberCardInfoExt, memberCardInfo);
                    }
                }
            }

            setBaseInfo(cardCoupon, memberCardInfo, coupon, couponTask);
            setAdvancedInfo(cardCoupon, memberCardInfo, coupon, couponTask);
        }

        return req;
    }

    private CreateCardReq getCardExt(ObjectNode ext, Integer taskTypeId) {
        if (!JsonUtil.isNullNode(ext)) {
            CreateCardReq req = new CreateCardReq();
            try {
                JsonNode cardNode = ext.path("card");
                if (!JsonUtil.isNullNode(cardNode)) {
                    if (WeixinConstant.CouponTaskConstant.TYPE_ID_MEMBER_CARD.equals(taskTypeId)) {
                        MemberCard memberCard = JsonUtil.jsonToObject(cardNode, MemberCard.class);
                        req.setCard(memberCard);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return req;
        }

        return null;
    }

    private void setBaseInfo(GetCardCouponResponse cardCoupon, CardInfoDto cardType, Coupon coupon, CouponTask couponTask) {
        Base_info base_info = new Base_info();
        cardType.setBase_info(base_info);

        base_info.setLogo_url(weixinConfig.getLogoUrl());
        base_info.setBrand_name("斯越里");
        base_info.setCode_type("CODE_TYPE_TEXT");
        base_info.setTitle(couponTask.getName());
        base_info.setColor(cardCoupon.getColor());
        base_info.setNotice(cardCoupon.getUseNotice());
        base_info.setService_phone(weixinConfig.getServicePhone());
        String desc = null;
        if (couponTask.getShareActivity() == 0) {
            desc = "不可与其他优惠共享";
        } else {
            desc = "可与其他优惠共享";
        }
        base_info.setDescription(desc);

        Date_info date_info = new Date_info();
        base_info.setDate_info(date_info);

        if (couponTask.getValidTimeStartAt() != null) {
            date_info.setType("DATE_TYPE_FIX_TIME_RANGE");
            date_info.setBegin_timestamp(couponTask.getValidTimeStartAt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
            date_info.setEnd_timestamp(couponTask.getValidTimeEndAt().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        } else if (couponTask.getReceiptStartDay() != null){
            date_info.setType("DATETYPE_FIX_TERM");
            date_info.setFixed_begin_term(couponTask.getReceiptStartDay());
            date_info.setFixed_term(couponTask.getReceiptEndDay());
        }


        Sku sku = new Sku();
        base_info.setSku(sku);

        sku.setQuantity(couponTask.getStockQty());

        base_info.setGet_limit(couponTask.getReceiptLimit());
        base_info.setUse_custom_code(false);
        base_info.setBind_openid(false);
        boolean canShare = false;
        boolean canGive = false;
        if (cardCoupon.getShareLink() == 1) {
            canShare = true;
        }
        if (cardCoupon.getShareGive() == 1) {
            canGive = true;
        }
        base_info.setCan_share(canShare);
        base_info.setCan_give_friend(canGive);

        /*List<Integer> location_id_list = new ArrayList<Integer>();
        location_id_list.add(123);
        location_id_list.add(12321);
        location_id_list.add(345345);
        base_info.setLocation_id_list(location_id_list);*/

        base_info.setCenter_title(cardCoupon.getCenterTitle());
        base_info.setCenter_sub_title(cardCoupon.getCenterSubTitle());
        base_info.setCenter_url(cardCoupon.getCenterUrl());
        base_info.setCustom_url_name(cardCoupon.getCustomEnterName());
        base_info.setCustom_url(cardCoupon.getCustomEnterUrl());
        base_info.setCustom_url_sub_title(cardCoupon.getCustomEnterGuide());
        /*base_info.setPromotion_url_name("更多优惠");
        base_info.setPromotion_url("http://www.qq.com");
        base_info.setSource("大众点评");*/


    }

    private void setAdvancedInfo(GetCardCouponResponse cardCoupon, CardInfoDto cardType, Coupon coupon, CouponTask couponTask) {
        Advanced_info advanced_info = new Advanced_info();
        cardType.setAdvanced_info(advanced_info);

        /*Use_condition use_condition = new Use_condition();
        advanced_info.setUse_condition(use_condition);

        use_condition.setAccept_category("鞋类");
        use_condition.setReject_category("阿迪达斯");
        use_condition.setCan_use_with_other_discount(true);*/

        Abs abs = new Abs();
        advanced_info.setAbs(abs);

        abs.setAbs(cardCoupon.getCoverIntro());
        List<String> icon_url_list = new ArrayList<String>();
        icon_url_list.add(cardCoupon.getCoverMaterialUrl());
        abs.setIcon_url_list(icon_url_list);

        /*List<Text_image_list> text_image_list = new ArrayList<Text_image_list>();
        advanced_info.setText_image_list(text_image_list);
        Text_image_list text_image = new Text_image_list();
        text_image.setImage_url("http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0");
        text_image.setText("此菜品精选食材，以独特的烹饪方法，最大程度地刺激食 客的味蕾");
        text_image_list.add(text_image);*/

        if (cardCoupon.getTimeLimitList() != null && cardCoupon.getTimeLimitList().size() > 0) {
            List<Time_limit> time_limit_list = new ArrayList<Time_limit>();
            advanced_info.setTime_limit(time_limit_list);
            for (TimeLimitVo timeLimitVo : cardCoupon.getTimeLimitList()) {
                Time_limit time_limit = new Time_limit();
                BeanUtils.copyProperties(timeLimitVo, time_limit);
                time_limit_list.add(time_limit);
            }
        }

        List<String> business_service = new ArrayList<String>();
        advanced_info.setBusiness_service(business_service);

        business_service.add("BIZ_SERVICE_FREE_WIFI");
        business_service.add("BIZ_SERVICE_WITH_PET");
        business_service.add("BIZ_SERVICE_FREE_PARK");
        business_service.add("BIZ_SERVICE_DELIVER");
    }
}
