package cn.siyue.platform.weixin.client.controller;


import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.weixin.client.service.MessageRedisService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMessageReplyClientService;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.*;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "微信_消息回复接口")
@RestController
@RequestMapping("/messageReply")
public class MessageReplyController extends BaseController {



    @Autowired
    private WeixinMessageReplyClientService weixinMessageReplyClientService;

    @Autowired
    private MessageRedisService messageRedisService;


    @LogAnnotation
    @ApiOperation(nickname = "addMessageReply",value = "新增消息回复")
    @PostMapping("/addMessageReply")
    public ResponseData addMessageReply(@Valid @RequestBody AddMessageReplyRequest requestParam, BindingResult result) {
        ResponseData errorResponse = getErrorResponse(result);
        if (errorResponse != null)
            return errorResponse;

        ResponseData respData = weixinMessageReplyClientService.addMessageReply(requestParam);
        invalidateCache(respData);
        return respData;
    }

    private void invalidateCache(ResponseData respData) {
        if (respData != null && ResponseBackCode.SUCCESS.getValue() == respData.getCode()) {
            messageRedisService.delMsgReplyList();
        }
    }

    @LogAnnotation
    @ApiOperation(nickname = "updateMessageReply",value = "修改消息回复")
    @PostMapping("/updateMessageReply")
    public ResponseData updateMessageReply(@Valid @RequestBody UpdateMessageReplyRequest requestParam, BindingResult result) {
        ResponseData errorResponse = getErrorResponse(result);
        if (errorResponse != null)
            return errorResponse;

        ResponseData respData = weixinMessageReplyClientService.updateMessageReply(requestParam);
        invalidateCache(respData);
        return respData;
    }

    @LogAnnotation
    @ApiOperation(nickname = "deleteMessageReply",value = "删除消息回复")
    @PostMapping("/deleteMessageReply")
    public ResponseData deleteMessageReply(@Valid @RequestBody DeleteMessageReplyRequest requestParam, BindingResult result) {
        ResponseData errorResponse = getErrorResponse(result);
        if (errorResponse != null)
            return errorResponse;

        ResponseData respData = weixinMessageReplyClientService.deleteMessageReply(requestParam);
        invalidateCache(respData);
        return respData;
    }

    @LogAnnotation
    @ApiOperation(nickname = "updateMessageReplyStatus",value = "修改消息回复状态")
    @PostMapping("/updateMessageReplyStatus")
    public ResponseData updateMessageReplyStatus(@Valid @RequestBody UpdateMessageReplyStatusRequest requestParam, BindingResult result) {
        ResponseData errorResponse = getErrorResponse(result);
        if (errorResponse != null)
            return errorResponse;

        ResponseData respData = weixinMessageReplyClientService.updateMessageReplyStatus(requestParam);
        invalidateCache(respData);
        return respData;
    }

    @LogAnnotation
    @ApiOperation(nickname = "getMessageReplyList",value = "得到消息回复列表")
    @PostMapping("/getMessageReplyList")
    public ResponseData<PageResponse<WeixinMessageReplyResponse>> getMessageReplyList(@Valid GetMessageReplyListRequest requestParam, BindingResult result) {
        ResponseData errorResponse = getErrorResponse(result);
        if (errorResponse != null)
            return errorResponse;

        return weixinMessageReplyClientService.getMessageReplyList(requestParam);
    }


}
