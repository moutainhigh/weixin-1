package cn.siyue.platform.weixin.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 微信消息回复表
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Data
@Accessors(chain = true)
@TableName("weixin_message_reply")
public class WeixinMessageReply extends Model<WeixinMessageReply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("1-被关注回复，2-无关键词回复，3-关键词回复")
    private Integer type;

    /**
     * 回复类型
     */
    @ApiModelProperty("1-文本(text)，2-图片（image），3-声音（voice），4-视频（video），5-图文（news）")
    @TableField("reply_type")
    private Integer replyType;
    /**
     * 匹配类型
     */
    @ApiModelProperty("1-全匹配，2-包含")
    @TableField("match_type")
    private Integer matchType;
    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String keyword;
    /**
     * 回复内容
     */
    @ApiModelProperty("回复内容")
    private String content;

    @TableField("media_id")
    private String mediaId;

    private String title;

    /**
     * 状态
     */
    @ApiModelProperty("0-禁用，1-启用")
    private Integer status;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private LocalDateTime createAt;
    /**
     * 更新时间
     */
    @TableField("update_at")
    private LocalDateTime updateAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
