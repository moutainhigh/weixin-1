package cn.siyue.platform.weixin.common.request.weixinmessagereply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CommonMessageReplyRequest {

    @ApiModelProperty(value = "类型: 1-被关注回复，2-无关键词回复，3-关键词回复")
    @NotNull(message = "类型不能为空")
    private Integer type;


    /**
     * 回复类型: 1-文本，2-图片，3-语音，4-视频，5-图文
     */
    @ApiModelProperty(value = "回复类型: 1-文本，2-图片，3-语音，4-视频，5-图文")
    @NotNull(message = "回复类型不能为空")
    private Integer replyType;
    /**
     * 匹配类型
     */
    @ApiModelProperty(value = "匹配类型: 1-全匹配，2-包含")
    private Integer matchType;
    /**
     * 关键词
     */
    private String keyword;
    /**
     * 回复内容
     */
    private String content;

    private String mediaId;

    private String title;


}
