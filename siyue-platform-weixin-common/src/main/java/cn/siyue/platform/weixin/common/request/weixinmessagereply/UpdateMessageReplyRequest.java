package cn.siyue.platform.weixin.common.request.weixinmessagereply;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateMessageReplyRequest extends CommonMessageReplyRequest {
    @NotNull(message = "id不能为空")
    private Long id;
}
