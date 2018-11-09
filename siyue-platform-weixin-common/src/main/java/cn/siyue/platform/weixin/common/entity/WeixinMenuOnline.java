package cn.siyue.platform.weixin.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微信菜单（与公众号同步）
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Data
@Accessors(chain = true)
@TableName("weixin_menu_online")
public class WeixinMenuOnline extends Model<WeixinMenuOnline> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 菜单KEY值
     */
    private String key;
    /**
     * 用户点击菜单可打开链接
     */
    private String url;
    /**
     * 多媒体id
     */
    @TableField("media_id")
    private String mediaId;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 回复类型
     */
    @TableField("reply_type")
    private Integer replyType;
    /**
     * 小程序的appid
     */
    @TableField("app_id")
    private String appId;
    /**
     * 小程序的页面路径
     */
    @TableField("page_path")
    private String pagePath;
    /**
     * 父id
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 素材标题
     */
    private String title;
    /**
     * 排序
     */
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
