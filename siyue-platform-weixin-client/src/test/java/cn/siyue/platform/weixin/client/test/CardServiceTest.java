package cn.siyue.platform.weixin.client.test;

import cn.siyue.platform.util.JsonUtil;
import cn.siyue.platform.weixin.client.WeixinClientApplication;
import cn.siyue.platform.weixin.common.config.WeixinConfig;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.advancedinfo.*;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Base_info;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Date_info;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Sku;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.*;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.ActionInfoDto;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.CardDto;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode.CreateCardQrcodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getusercardlist.GetCardListReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.batchget.BatchGetReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.code.GetCodeReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.getcard.GetCardReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.setselfconsumecell.SetSelfConsumeCellReq;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.settestwhitelist.SetTestWhiteListReq;
import cn.siyue.platform.weixin.common.service.CardService;
import com.fasterxml.jackson.databind.JsonNode;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeixinClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private WeixinConfig weixinConfig;

    @Test
    public void testUploadImg() {
        File file = new File("/Users/admin/work_test/img/card_logo_4.png");
        WxMediaImgUploadResult result = cardService.uploadImg(file);
        String content = null;
        try {
            content = JsonUtil.toJsonString(result);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateCard() {
        CreateCardReq req = getCardInfo("CASH");
        //cardService.create(req);
    }

    @Test
    public void testGetCode() {
        GetCodeReq req = new GetCodeReq();
        req.setCard_id("peEbOv1HKl0NcDdq7-j7FV3ePDsU");
        req.setCode("110201201245");
        try {
            cardService.getCode(req);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCard() {
        GetCardReq req = new GetCardReq();
        String cardId = "peEbOv1HKl0NcDdq7-j7FV3ePDsU";
        req.setCard_id(cardId);
        try {
            Card content = cardService.getCard(req);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchGet() {
        BatchGetReq req = new BatchGetReq();
        req.setOffset(0);
        req.setCount(10);
        try {
            cardService.batchGet(req);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCardList() {
        GetCardListReq req = new GetCardListReq();
        String cardId = "peEbOv1HKl0NcDdq7-j7FV3ePDsU";
        req.setOpenid("");
        req.setCard_id(cardId);
        try {
            cardService.getUserCardList(req);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendByOpenId() {
        String openId = "oeEbOv7u3MlMrEgayNti7U-Swjzo";
        String cardId = "peEbOvx94il5ZqRiYaYm8h18IMLs";
        List<String> openIds = new ArrayList<String>();
        openIds.add(openId);
        openIds.add(openId);
        WxMpMassSendResult result = cardService.sendByOpenId(openIds, cardId);
        String content = null;
        try {
            content = JsonUtil.toJsonString(result);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateCardQrcode() {
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGB7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyeEU2M2s1VTZiZV8xbDdXVzFyNEIAAgQ-c3pbAwQIBwAA
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQE98DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyOXVMZWs1VTZiZV8xcXhkV2hyNFYAAgSZhnpbAwQIBwAA
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGC8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNGIzQ2szVTZiZV8xcjBnV3hyNHgAAgS4iXpbAwQIBwAA
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQHa7jwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyejlKZGttVTZiZV8xa1lNLWhyNGMAAgQ0aX5bAwQIBwAA
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGc8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyaFhpVmw3VTZiZV8xcUo5LWhyNEsAAgSlgn5bAwQIBwAA
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGX8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyMmlFQWszVTZiZV8xcndhLU5yNEMAAgTYg35bAwQIBwAA
        // https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFv8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyZDZkU2w0VTZiZV8xcW5kLWhyNGwAAgSPhn5bAwQIBwAA
        CreateCardQrcodeReq req = new CreateCardQrcodeReq();
        req.setAction_name("QR_CARD");
        req.setExpire_seconds(1800);
        ActionInfoDto actionInfo = new ActionInfoDto();
        req.setAction_info(actionInfo);
        CardDto card = new CardDto();
        actionInfo.setCard(card);
        String openId = "oeEbOv7u3MlMrEgayNti7U-Swjzo";
        //String cardId = "peEbOvx94il5ZqRiYaYm8h18IMLs";
        // String cardId = "peEbOv1HKl0NcDdq7-j7FV3ePDsU";
        //String cardId = "peEbOv2YUBGQuC3UYvyjAaXJUKgQ";
        //String cardId = "peEbOv0qMBJBcikBHcBKeWO5Eg50";
        String cardId = "peEbOvzbyQonqKrv-5jbekp2NLEw";

        card.setCard_id(cardId);
        card.setOpenid(openId);
        card.setIs_unique_code(false);
        card.setOuter_str("12b");

        try {
            cardService.createCardQrcode(req);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetTestWhiteList() {
        SetTestWhiteListReq req = new SetTestWhiteListReq();
        List<String> openids = new ArrayList<String>();
        String openId = "oeEbOv7u3MlMrEgayNti7U-Swjzo";
        openids.add(openId);
        req.setOpenid(openids);
        cardService.setTestWhiteList(req);
    }

    @Test
    public void testSetSelfConsumeCell() {
        SetSelfConsumeCellReq req = new SetSelfConsumeCellReq();
        //String cardId = "peEbOv2YUBGQuC3UYvyjAaXJUKgQ";
        String cardId = "peEbOvzbyQonqKrv-5jbekp2NLEw";
        req.setCard_id(cardId);
        req.setIs_open(true);
        /*req.setNeed_remark_amount(false);
        req.setNeed_verify_cod(false);*/
        cardService.setSelfConsumeCell(req);
    }

    private CreateCardReq getCardInfo(String cardType) {
        CreateCardReq req = new CreateCardReq();

        if (cardType.equals("CASH")) {
            CashCard card = new CashCard();
            req.setCard(card);
            card.setCard_type("CASH");
            CashCardInfoDto cash = new CashCardInfoDto();
            card.setCash(cash);
            cash.setLeast_cost(1000);
            cash.setReduce_cost(100);
            setCardTypeInfo(cash);
        } else {
            GrouponCard card = new GrouponCard();
            req.setCard(card);
            GrouponCardInfoDto groupon = new GrouponCardInfoDto();
            card.setGroupon(groupon);
            setCardTypeInfo(groupon);
            setAdvancedInfo(groupon);
            groupon.setDeal_detail("以下锅底2选1（有菌王锅、麻辣锅、大骨锅、番茄锅、清补 凉锅、酸菜鱼锅可选）：\\n大锅1份 12元\\n小锅2份 16元 ");
        }

        return req;
    }

    private void setCardTypeInfo(CardInfoDto cardType) {
        Base_info base_info = new Base_info();
        cardType.setBase_info(base_info);

        base_info.setLogo_url("http://mmbiz.qpic.cn/mmbiz_png/icExomlPKVtqYe0GmqyXSuJQicbDm1we3RRFibpZje4kIfVTTq6OExZozia3pCagGZD8TI0BaNVGXibMoibauHEGibLdA/0");
        base_info.setBrand_name("斯越里");
        base_info.setCode_type("CODE_TYPE_TEXT");
        base_info.setTitle("132元双人火锅套餐");
        base_info.setColor("Color010");
        base_info.setNotice("使用时向服务员出示此券");
        base_info.setService_phone("020-88888888");
        base_info.setDescription("不可与其他优惠同享\\n如需团购券发票，请在消费时向商户提出\\n店内均可使用，仅限堂食");

        Date_info date_info = new Date_info();
        base_info.setDate_info(date_info);

        date_info.setType("DATE_TYPE_FIX_TIME_RANGE");
        long curTime = System.currentTimeMillis() / 1000;
        date_info.setBegin_timestamp(curTime - 60 * 60 * 24 * 7);
        date_info.setEnd_timestamp(curTime + 60 * 60 * 24 * 7);


        Sku sku = new Sku();
        base_info.setSku(sku);

        sku.setQuantity(500000);

        base_info.setUse_limit(100);
        base_info.setGet_limit(3);
        base_info.setUse_custom_code(false);
        base_info.setBind_openid(false);
        base_info.setCan_share(true);
        base_info.setCan_give_friend(true);

        List<Integer> location_id_list = new ArrayList<Integer>();
        location_id_list.add(123);
        location_id_list.add(12321);
        location_id_list.add(345345);
        base_info.setLocation_id_list(location_id_list);

        base_info.setCenter_title("顶部居中按钮");
        base_info.setCenter_sub_title("按钮下方的wording");
        base_info.setCenter_url("www.qq.com");
        base_info.setCustom_url_name("立即使用");
        base_info.setCustom_url("http://www.qq.com");
        base_info.setCustom_url_sub_title("6个汉字tips");
        base_info.setPromotion_url_name("更多优惠");
        base_info.setPromotion_url("http://www.qq.com");
        base_info.setSource("大众点评");


    }

    private void setAdvancedInfo(CardInfoDto cardType) {
        Advanced_info advanced_info = new Advanced_info();
        cardType.setAdvanced_info(advanced_info);

        Use_condition use_condition = new Use_condition();
        advanced_info.setUse_condition(use_condition);

        use_condition.setAccept_category("鞋类");
        use_condition.setReject_category("阿迪达斯");
        use_condition.setCan_use_with_other_discount(true);

        Abs abs = new Abs();
        advanced_info.setAbs(abs);

        abs.setAbs("微信餐厅推出多种新季菜品，期待您的光临");
        List<String> icon_url_list = new ArrayList<String>();
        icon_url_list.add("http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0");
        abs.setIcon_url_list(icon_url_list);

        List<Text_image_list> text_image_list = new ArrayList<Text_image_list>();
        advanced_info.setText_image_list(text_image_list);
        Text_image_list text_image = new Text_image_list();
        text_image.setImage_url("http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0");
        text_image.setText("此菜品精选食材，以独特的烹饪方法，最大程度地刺激食 客的味蕾");
        text_image_list.add(text_image);

        List<Time_limit> time_limit_list = new ArrayList<Time_limit>();
        advanced_info.setTime_limit(time_limit_list);

        Time_limit time_limit = new Time_limit();
        time_limit.setType("MONDAY");
        time_limit.setBegin_hour(0);
        time_limit.setEnd_hour(10);
        time_limit.setBegin_minute(10);
        time_limit.setEnd_minute(59);
        time_limit_list.add(time_limit);
        time_limit = new Time_limit();
        time_limit.setType("HOLIDAY");
        time_limit_list.add(time_limit);
        List<String> business_service = new ArrayList<String>();
        advanced_info.setBusiness_service(business_service);

        business_service.add("BIZ_SERVICE_FREE_WIFI");
        business_service.add("BIZ_SERVICE_WITH_PET");
        business_service.add("BIZ_SERVICE_FREE_PARK");
        business_service.add("BIZ_SERVICE_DELIVER");
    }

}
