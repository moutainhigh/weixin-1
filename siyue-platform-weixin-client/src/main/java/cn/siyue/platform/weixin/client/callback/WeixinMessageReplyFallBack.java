package cn.siyue.platform.weixin.client.callback;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMessageReplyClientService;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.*;
import org.springframework.stereotype.Component;

@Component
public class WeixinMessageReplyFallBack extends BaseServiceFallBack implements WeixinMessageReplyClientService {
    @Override
    public ResponseData getMessageReplyList(GetMessageReplyListRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData addMessageReply(AddMessageReplyRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData updateMessageReply(UpdateMessageReplyRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData getMessageReply(GetMessageReplyRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData updateMessageReplyStatus(UpdateMessageReplyStatusRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData deleteMessageReply(DeleteMessageReplyRequest requestParam) {
        return getDownGradeResponse();
    }
}
