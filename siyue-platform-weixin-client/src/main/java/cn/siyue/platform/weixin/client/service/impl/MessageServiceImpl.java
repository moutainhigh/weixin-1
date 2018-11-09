package cn.siyue.platform.weixin.client.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.client.service.MessageService;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponClientService;
import cn.siyue.platform.weixin.client.service.siyueli.member.CouponTaskClientService;
import cn.siyue.platform.weixin.client.service.siyueli.member.MemberBackendUserService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinCardCouponClientService;
import cn.siyue.platform.weixin.common.constants.MessageReplyConstant;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.dto.message.ReplyInfoDto;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.GetCardCouponRequest;
import cn.siyue.platform.weixin.common.response.weixincardcoupon.GetCardCouponResponse;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import cn.siyue.platform.weixin.common.service.MaterialService;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import com.siyueli.platform.member.request.coupon.AddCouponRequest;
import com.siyueli.platform.member.request.coupon.GetCouponRequest;
import com.siyueli.platform.member.response.coupon.CouponResponse;
import com.siyueli.platform.member.response.coupontask.GetCouponTaskResponse;
import com.siyueli.platform.member.response.member.getuser.GetUserResponse;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private WeixinMpService weixinMpService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CouponClientService couponClientService;

    @Autowired
    private CouponTaskClientService couponTaskClientService;

    @Autowired
    private MemberBackendUserService memberBackendUserService;

    @Autowired
    private WeixinCardCouponClientService weixinCardCouponClientService;

    private void handleClickEvent(WxMpMessageRouter wxMpMessageRouter, List<WeixinMenuOnlineVo> menuList) {
        if (menuList != null && menuList.size() > 0) {
            for (WeixinMenuOnlineVo menuData : menuList) {
                if (!"click".equals(menuData.getType()))
                    continue;

                WxMpMessageHandler handler = getClickHandler(menuData);
                wxMpMessageRouter
                        .rule()
                        .async(false)
                        .msgType(WxConsts.XmlMsgType.EVENT)
                        .event(WxConsts.EventType.CLICK)
                        .eventKey(menuData.getKey())
                        .handler(handler)
                        .end();
            }
        }

    }

    private WxMpMessageHandler getClickHandler(WeixinMenuOnlineVo menuData) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                ReplyInfoDto replyInfoDto = new ReplyInfoDto();
                replyInfoDto.setMediaId(menuData.getMediaId());
                replyInfoDto.setReplyType(menuData.getReplyType());
                return getWxMpXmlOutMessage(wxMessage, replyInfoDto);
            }
        };
        return handler;
    }

    private void handleSubscribeEvent(WxMpMessageRouter wxMpMessageRouter, WeixinMessageReplyResponse subscibeReply) {
        WxMpMessageHandler subscribeHandler = getSubscribeHandler(subscibeReply);
        wxMpMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler)
                .end();
    }

    private void handleUserGetCardEvent(WxMpMessageRouter wxMpMessageRouter) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                // 领取的卡券增加到数据表中
                addCoupon(wxMessage);

                String replyContent = "领取卡券成功";
                WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;
            }
        };

        wxMpMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event("user_get_card")
                .handler(handler)
                .end();
    }

    private void handleUserConsumeCardEvent(WxMpMessageRouter wxMpMessageRouter) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                // 更新状态为已核销
                updateCouponStatus(wxMessage, WeixinConstant.CouponConstant.STATUS_ID_CONSUME);

                String replyContent = "核销卡券成功";
                WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;
            }
        };

        wxMpMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event("user_consume_card")
                .handler(handler)
                .end();
    }

    private void handleUserDelCardEvent(WxMpMessageRouter wxMpMessageRouter) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                // 更新状态为已删除
                updateCouponStatus(wxMessage, WeixinConstant.CouponConstant.STATUS_ID_DELETE);

                String replyContent = "删除卡券成功";
                WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;
            }
        };

        wxMpMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event("user_del_card")
                .handler(handler)
                .end();
    }

    private void handleCardPassCheckEvent(WxMpMessageRouter wxMpMessageRouter) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                // 更新状态为审核通过
                updateWeixinCardCouponStatus(wxMessage, WeixinConstant.CardCouponConstant.STATUS_CARD_PASS_CHECK);

                String replyContent = "卡券审核通过";
                /*WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;*/
                System.out.println(replyContent);
                return null;
            }
        };

        wxMpMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event("card_pass_check")
                .handler(handler)
                .end();
    }

    private void handleCardNotPassCheckEvent(WxMpMessageRouter wxMpMessageRouter) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                updateCouponStatus(wxMessage, WeixinConstant.CardCouponConstant.STATUS_CARD_NOT_PASS_CHECK);
                String replyContent = "卡券审核不通过";
                /*WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;*/
                System.out.println(replyContent);
                return null;
            }
        };

        wxMpMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event("card_not_pass_check")
                .handler(handler)
                .end();
    }

    private void updateWeixinCardCouponStatus(WxMpXmlMessage wxMessage, Integer status) {
        GetCardCouponRequest getReq = new GetCardCouponRequest();
        getReq.setCardId(wxMessage.getCardId());
        ResponseData<GetCardCouponResponse> respData = weixinCardCouponClientService.get(getReq);
        if (ResponseUtil.isSuccess(respData)) {
            GetCardCouponResponse weixinCardCoupon = (GetCardCouponResponse)respData.getData();

            if (weixinCardCoupon != null) {
                weixinCardCouponClientService.updateStatus(weixinCardCoupon.getId(), status);
            }
        }


    }

    private void updateCouponStatus(WxMpXmlMessage wxMessage, Integer status) {
        GetCouponRequest req = new GetCouponRequest();
        req.setCode(wxMessage.getUserCardCode());
        ResponseData<CouponResponse> couponRespData = couponClientService.getCoupon(req);

        if (ResponseUtil.isSuccess(couponRespData)) {
            CouponResponse coupon = (CouponResponse)couponRespData.getData();
            if (coupon != null) {
                couponClientService.updateCouponStatus(coupon.getId(), status);
            }
        }
    }

    private void addCoupon(WxMpXmlMessage wxMessage) {
        ResponseData<GetCouponTaskResponse> couponTaskRespData = couponTaskClientService.getCouponTaskByCardId(wxMessage.getCardId());
        if (ResponseUtil.isSuccess(couponTaskRespData)) {
            System.out.println("openId: " + wxMessage.getOpenId());
            ResponseData<GetUserResponse> muRespData = memberBackendUserService.getUserByOpenId(wxMessage.getOpenId());
            if (ResponseUtil.isSuccess(muRespData)) {
                GetUserResponse memberUser = (GetUserResponse)muRespData.getData();
                GetCouponTaskResponse couponTask = (GetCouponTaskResponse)couponTaskRespData.getData();
                if (memberUser != null && couponTask != null) {
                    AddCouponRequest req = new AddCouponRequest();
                    LocalDateTime now = LocalDateTime.now();
                    req.setCode(wxMessage.getUserCardCode());
                    req.setMemberId(memberUser.getId());
                    req.setTaskId(couponTask.getId());
                    req.setReceiptAt(now);
                    req.setStatusId(WeixinConstant.CouponConstant.STATUS_ID_RECEIPT);
                    req.setTaskTypeId(couponTask.getTypeId());
                    req.setValidTimeStartAt(couponTask.getValidTimeStartAt());
                    req.setValidTimeEndAt(couponTask.getValidTimeEndAt());
                    couponClientService.add(req);
                }

            }


        }
    }

    @Override
    public WxMpMessageRouter createWxMpMessageRouter(WxMpService wxMpService, List<WeixinMessageReplyResponse> list, List<WeixinMenuOnlineVo> menuList) {
        WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(weixinMpService.getWxMpService());
        handleClickEvent(wxMpMessageRouter, menuList);
        handleUserGetCardEvent(wxMpMessageRouter);
        handleUserConsumeCardEvent(wxMpMessageRouter);
        handleCardPassCheckEvent(wxMpMessageRouter);
        handleCardNotPassCheckEvent(wxMpMessageRouter);
        handleUserDelCardEvent(wxMpMessageRouter);
        handleContentReply(wxMpMessageRouter, list);


        return wxMpMessageRouter;
    }

    private void handleContentReply(WxMpMessageRouter wxMpMessageRouter, List<WeixinMessageReplyResponse> list) {
        if (list != null && !list.isEmpty()) {
            // 关注回复
            WeixinMessageReplyResponse subscibeReply = findByType(MessageReplyConstant.Type.SUBSCRIBE_REPLY, list);
            handleSubscribeEvent(wxMpMessageRouter, subscibeReply);

            // 关键词回复
            handleKeywordReply(wxMpMessageRouter, list);

            // 无关键词回复
            WeixinMessageReplyResponse noKeywordReply = findByType(MessageReplyConstant.Type.NO_KEYWORD_REPLY, list);
            handleDefault(wxMpMessageRouter, noKeywordReply);
        } else {
            handleDefault(wxMpMessageRouter, null);
        }
    }

    private void handleKeywordReply(WxMpMessageRouter wxMpMessageRouter, List<WeixinMessageReplyResponse> list) {
        for (WeixinMessageReplyResponse replyInfo : list) {
            if (!replyInfo.getType().equals(MessageReplyConstant.Type.KEYWORD_REPLY))
                continue;

            WxMpMessageHandler handler = new WxMpMessageHandler() {
                @Override
                public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                    if (replyInfo.getReplyType().equals(MessageReplyConstant.ReplyType.TEXT)) {
                        WxMpXmlOutTextMessage m
                                = WxMpXmlOutMessage.TEXT().content(replyInfo.getContent()).fromUser(wxMessage.getToUser())
                                .toUser(wxMessage.getFromUser()).build();
                        return m;
                    } else {
                        ReplyInfoDto replyInfoDto = new ReplyInfoDto();
                        replyInfoDto.setMediaId(replyInfo.getMediaId());
                        replyInfoDto.setReplyType(replyInfo.getReplyType());
                        return getWxMpXmlOutMessage(wxMessage, replyInfoDto);
                    }
                }
            };


            // 全匹配
            if (replyInfo.getMatchType().equals(MessageReplyConstant.MatchType.FULL)) {
                wxMpMessageRouter
                        .rule()
                        .async(false)
                        .content(replyInfo.getKeyword()) // 拦截内容为“哈哈”的消息
                        .handler(handler)
                        .end();
            } else if (replyInfo.getMatchType().equals(MessageReplyConstant.MatchType.CONTAIN)) { // 包含
                wxMpMessageRouter
                        .rule()
                        .async(false)
                        .rContent("(\\s*\\S*)*" + replyInfo.getKeyword() + "(\\s*\\S*)*")
                        .handler(handler)
                        .end();
            }
        }

    }

    private void handleDefault(WxMpMessageRouter wxMpMessageRouter, WeixinMessageReplyResponse noKeywordReply) {
        WxMpMessageHandler noKeywordHandler = getNoKeywordHandler(noKeywordReply);
        wxMpMessageRouter
                .rule()
                .async(false)
                .handler(noKeywordHandler)
                .end();
    }

    private WxMpXmlOutMessage getWxMpXmlOutMessage(WxMpXmlMessage wxMessage, ReplyInfoDto replyInfo) {
        if (MessageReplyConstant.ReplyType.IMAGE.equals(replyInfo.getReplyType())) {
            WxMpXmlOutMessage m = WxMpXmlOutMessage.IMAGE().mediaId(replyInfo.getMediaId()).fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            return m;
        }
        if (MessageReplyConstant.ReplyType.VOICE.equals(replyInfo.getReplyType())) {
            WxMpXmlOutMessage m = WxMpXmlOutMessage.VOICE().mediaId(replyInfo.getMediaId()).fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            return m;
        }
        if (MessageReplyConstant.ReplyType.VIDEO.equals(replyInfo.getReplyType())) {
            WxMpXmlOutMessage m = WxMpXmlOutMessage.VIDEO().mediaId(replyInfo.getMediaId()).fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            return m;
        }

        // 图文消息
        if (MessageReplyConstant.ReplyType.NEWS.equals(replyInfo.getReplyType())) {
            WxMpMaterialNews newsInfo = materialService.getNews(replyInfo.getMediaId());
            if (newsInfo != null && newsInfo.getArticles() != null && newsInfo.getArticles().size() > 0) {
                List<WxMpXmlOutNewsMessage.Item> articleList = new ArrayList<WxMpXmlOutNewsMessage.Item>();
                for (WxMpMaterialNews.WxMpMaterialNewsArticle article : newsInfo.getArticles()) {
                    WxMpXmlOutNewsMessage.Item newsItem = new WxMpXmlOutNewsMessage.Item();
                    newsItem.setTitle(article.getTitle());
                    newsItem.setDescription(article.getDigest());
                    newsItem.setPicUrl(article.getThumbUrl());
                    newsItem.setUrl(article.getUrl());
                    articleList.add(newsItem);
                }

                WxMpXmlOutMessage m = WxMpXmlOutMessage.NEWS().articles(articleList).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;
            }
        }
        return null;
    }

    private WxMpMessageHandler getNoKeywordHandler(WeixinMessageReplyResponse noKeywordReply) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                String replyContent = "你好！";
                if (noKeywordReply != null && StringUtils.isNotEmpty(noKeywordReply.getContent())) {
                    replyContent = noKeywordReply.getContent();
                }

                WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;
            }
        };
        return handler;
    }

    private WxMpMessageHandler getSubscribeHandler(WeixinMessageReplyResponse subscibeReply) {
        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
                String replyContent = "谢谢关注！";
                if (subscibeReply != null && StringUtils.isNotEmpty(subscibeReply.getContent())) {
                    replyContent = subscibeReply.getContent();
                }

                WxMpXmlOutTextMessage m
                        = WxMpXmlOutMessage.TEXT().content(replyContent).fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
                return m;
            }
        };
        return handler;
    }

    private WeixinMessageReplyResponse findByType(int type, List<WeixinMessageReplyResponse> list) {
        if (list != null && !list.isEmpty()) {
            for (WeixinMessageReplyResponse replyInfo : list) {
                if (replyInfo.getType() == type) {
                    return replyInfo;
                }
            }
        }
        return null;
    }
}
