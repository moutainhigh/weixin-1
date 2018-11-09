package cn.siyue.platform.weixin.service.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.*;
import cn.siyue.platform.weixin.service.service.WeixinMessageReplyServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixinMessageReply")
public class WeixinMessageReplyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinMessageReplyController.class);

    @Autowired
    private WeixinMessageReplyServiceContract weixinMessageReplyServiceContract;

    @LogAnnotation
    @PostMapping(value = "/getMessageReplyList", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseData getMessageReplyList(@RequestBody GetMessageReplyListRequest requestParam) {
        try {
            return weixinMessageReplyServiceContract.getMessageReplyList(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/getMessageReply")
    public ResponseData getMessageReply(@RequestBody GetMessageReplyRequest requestParam) {
        try {
            return weixinMessageReplyServiceContract.getMessageReply(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/addMessageReply")
    public ResponseData addMessageReply(@RequestBody AddMessageReplyRequest requestParam) {
        try {
            return weixinMessageReplyServiceContract.addMessageReply(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/updateMessageReply")
    public ResponseData updateMessageReply(@RequestBody UpdateMessageReplyRequest requestParam) {
        try {
            return weixinMessageReplyServiceContract.updateMessageReply(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/deleteMessageReply")
    public ResponseData deleteMessageReply(@RequestBody DeleteMessageReplyRequest requestParam) {
        try {
            return weixinMessageReplyServiceContract.deleteMessageReply(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/updateMessageReplyStatus")
    public ResponseData updateMessageReplyStatus(@RequestBody UpdateMessageReplyStatusRequest requestParam) {
        try {
            return weixinMessageReplyServiceContract.updateMessageReplyStatus(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }
}
