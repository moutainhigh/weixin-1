package cn.siyue.platform.weixin.common.request.weixinmessagereply;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateMessageReplyStatusRequest {

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
