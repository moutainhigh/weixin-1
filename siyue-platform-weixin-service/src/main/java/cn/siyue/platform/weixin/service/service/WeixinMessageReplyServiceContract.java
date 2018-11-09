package cn.siyue.platform.weixin.service.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.entity.WeixinMessageReply;
import cn.siyue.platform.weixin.common.request.weixinmessagereply.*;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 微信消息回复表 服务类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
public interface WeixinMessageReplyServiceContract extends IService<WeixinMessageReply> {

    public ResponseData getMessageReplyList(GetMessageReplyListRequest requestParam);

    public ResponseData addMessageReply(AddMessageReplyRequest requestParam);

    public ResponseData updateMessageReply(UpdateMessageReplyRequest requestParam);

    public ResponseData getMessageReply(GetMessageReplyRequest requestParam);

    public ResponseData updateMessageReplyStatus(UpdateMessageReplyStatusRequest requestParam);

    public ResponseData deleteMessageReply(DeleteMessageReplyRequest requestParam);
}
