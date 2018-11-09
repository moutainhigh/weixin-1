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
import java.util.Date;

/**
 * <p>
 * 微信菜单表
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Data
@Accessors(chain = true)
@TableName("weixin_menu")
public class WeixinMenu extends Model<WeixinMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类型
     */
    @ApiModelProperty("类型: click-消息推送，view-url, miniprogram-小程序")
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 菜单KEY值
     */
    @ApiModelProperty("菜单KEY值, 消息推送类型必填")
    private String key;
    /**
     * 用户点击菜单可打开链接
     */
    @ApiModelProperty("用户点击菜单可打开链接, url类型必填")
    private String url;
    /**
     * 多媒体id
     */
    @ApiModelProperty("多媒体id")
    @TableField("media_id")
    private String mediaId;
    @ApiModelProperty("1-文本(text)，2-图片（image），3-声音（voice），4-视频（video），5-图文（news）")
    @TableField("reply_type")
    private Integer replyType;
    /**
     * 小程序的appid
     */
    @ApiModelProperty("小程序的appid")
    @TableField("app_id")
    private String appId;
    /**
     * 小程序的页面路径
     */
    @ApiModelProperty("小程序的页面路径")
    @TableField("page_path")
    private String pagePath;
    /**
     * 父id
     */
    @ApiModelProperty("父id,一级菜单为0")
    @TableField("parent_id")
    private Long parentId;
    /**
     * 素材标题
     */
    @ApiModelProperty("素材标题")
    private String title;

    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
    /**
     * 更新时间
     */
    @TableField("update_at")
    private Date updateAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
