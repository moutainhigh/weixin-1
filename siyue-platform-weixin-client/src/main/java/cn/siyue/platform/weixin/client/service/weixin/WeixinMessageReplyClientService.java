package cn.siyue.platform.weixin.client.service.weixin;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.callback.WeixinMessageReplyFallBack;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.*;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "weixin-service", fallback = WeixinMessageReplyFallBack.class)
public interface WeixinMessageReplyClientService {

    @RequestMapping(value = "/weixinMessageReply/getMessageReplyList", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseData<PageResponse<WeixinMessageReplyResponse>> getMessageReplyList(@RequestBody GetMessageReplyListRequest requestParam);

    @RequestMapping(value = "/weixinMessageReply/addMessageReply", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData addMessageReply(@RequestBody AddMessageReplyRequest requestParam);

    @RequestMapping(value = "/weixinMessageReply/updateMessageReply", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData updateMessageReply(@RequestBody UpdateMessageReplyRequest requestParam);

    @RequestMapping(value = "/weixinMessageReply/getMessageReply", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData getMessageReply(@RequestBody GetMessageReplyRequest requestParam);

    @RequestMapping(value = "/weixinMessageReply/updateMessageReplyStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData updateMessageReplyStatus(@RequestBody UpdateMessageReplyStatusRequest requestParam);

    @RequestMapping(value = "/weixinMessageReply/deleteMessageReply", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData deleteMessageReply(@RequestBody DeleteMessageReplyRequest requestParam);
}
