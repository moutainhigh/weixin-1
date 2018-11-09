package cn.siyue.platform.weixin.client.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;
import cn.siyue.platform.weixin.client.service.MenuRedisService;
import cn.siyue.platform.weixin.client.service.MessageRedisService;
import cn.siyue.platform.weixin.client.service.MessageService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMenuClientService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMenuOnlineClientService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMessageReplyClientService;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.config.WeixinConfig;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.GetMessageReplyListRequest;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "微信_接收消息接口")
@Controller
@RequestMapping("/message")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private WeixinMpService weixinMpService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private WeixinMessageReplyClientService weixinMessageReplyClientService;

    @Autowired
    private WeixinMenuOnlineClientService weixinMenuOnlineClientService;

    @Autowired
    private MenuRedisService menuRedisService;

    @Autowired
    private MessageRedisService messageRedisService;



    //@LogAnnotation
    @ApiOperation(nickname = "receive",value = "接收消息")
    @RequestMapping(value = "/receive", method = {RequestMethod.GET, RequestMethod.POST})
    public void receive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contentType = request.getContentType();
        LOGGER.info("content-type: " + contentType);

        WxMpService wxMpService = weixinMpService.getWxMpService();

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }

        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(echostr);
            return;
        }


        // 待优化，数据可以缓存起来
        List<WeixinMessageReplyResponse> list = messageRedisService.getMsgReplyList();
        if (list == null || list.isEmpty()) {
            GetMessageReplyListRequest requestParam = new GetMessageReplyListRequest();
            requestParam.setPage(WeixinConstant.PAGE_ONE);
            requestParam.setSize(WeixinConstant.PAGE_SIZE_ALL);
            ResponseData<PageResponse<WeixinMessageReplyResponse>> respData = weixinMessageReplyClientService.getMessageReplyList(requestParam);
            if (respData != null && ResponseBackCode.SUCCESS.getValue() == respData.getCode()) {
                PageResponse<WeixinMessageReplyResponse> pageResponse = (PageResponse<WeixinMessageReplyResponse>) respData.getData();
                if (pageResponse != null) {
                    list = pageResponse.getRecords();
                    messageRedisService.saveMsgReplyList(list);
                }
            }
        }

        List<WeixinMenuOnlineVo> menuList = menuRedisService.getMenuList();
        if (menuList == null || menuList.isEmpty()) {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPage(WeixinConstant.PAGE_ONE);
            pageRequest.setSize(WeixinConstant.PAGE_SIZE_ALL);
            ResponseData<PageResponse<WeixinMenuOnlineVo>> menuListRespData = weixinMenuOnlineClientService.getList(pageRequest);

            if (menuListRespData != null && ResponseBackCode.SUCCESS.getValue() == menuListRespData.getCode()) {
                PageResponse<WeixinMenuOnlineVo> menuPageResp = (PageResponse<WeixinMenuOnlineVo>) menuListRespData.getData();
                if (menuPageResp != null) {
                    menuList = menuPageResp.getRecords();
                    menuRedisService.saveMenuList(menuList);
                }
            }
        }

        WxMpMessageRouter wxMpMessageRouter = messageService.createWxMpMessageRouter(wxMpService, list, menuList);
        WxMpConfigStorage config = wxMpService.getWxMpConfigStorage();


        response.setContentType("application/xml;charset=UTF-8");

        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ?
                "raw" :
                request.getParameter("encrypt_type");

        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            response.getWriter().write(outMessage.toXml());
            return;
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            String msgSignature = request.getParameter("msg_signature");
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            response.getWriter().write(outMessage.toEncryptedXml(config));
            return;
        }

        response.getWriter().println("不可识别的加密类型");
        return;
    }
}
