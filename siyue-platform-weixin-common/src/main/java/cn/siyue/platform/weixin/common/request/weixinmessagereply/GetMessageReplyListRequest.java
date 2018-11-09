package cn.siyue.platform.weixin.common.request.weixinmessagereply;

import cn.siyue.platform.weixin.common.common.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetMessageReplyListRequest extends PageRequest {

    @ApiModelProperty(value = "类型: 1-被关注回复，2-无关键词回复，3-关键词回复")
    private Integer type;
}
