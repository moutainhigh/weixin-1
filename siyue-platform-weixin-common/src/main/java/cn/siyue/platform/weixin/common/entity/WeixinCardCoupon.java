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
 * 微信卡券表
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Data
@Accessors(chain = true)
@TableName("weixin_card_coupon")
public class WeixinCardCoupon extends Model<WeixinCardCoupon> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 卡券任务id
     */
    @TableField("task_id")
    private Long taskId;
    /**
     * 卡券颜色
     */
    private String color;
    /**
     * 封面图片url
     */
    @TableField("cover_image_url")
    private String coverImageUrl;
    /**
     * 封面图片密钥
     */
    @TableField("cover_image_secret")
    private String coverImageSecret;

    @TableField("cover_material_url")
    private String coverMaterialUrl;
    /**
     * 封面简介
     */
    @TableField("cover_intro")
    private String coverIntro;
    /**
     * 使用须知
     */
    @TableField("use_notice")
    private String useNotice;

    @TableField("time_limit")
    private String timeLimit;



    /**
     * center_title
     */
    @TableField("center_title")
    private String centerTitle;
    /**
     * center_sub_title
     */
    @TableField("center_sub_title")
    private String centerSubTitle;
    /**
     * center入口url
     */
    @TableField("center_url")
    private String centerUrl;
    /**
     * center入口appid
     */
    @TableField("center_appid")
    private String centerAppid;
    /**
     * center入口路径
     */
    @TableField("center_page_path")
    private String centerPagePath;
    /**
     * 自定义入口名称
     */
    @TableField("custom_enter_name")
    private String customEnterName;
    /**
     * 自定义入口引导语
     */
    @TableField("custom_enter_guide")
    private String customEnterGuide;
    /**
     * 自定义入口URL
     */
    @TableField("custom_enter_url")
    private String customEnterUrl;
    /**
     * 用户可以分享领券链接
     */
    @TableField("share_link")
    private Integer shareLink;
    /**
     * 用户领券后可转赠其他好友
     */
    @TableField("share_give")
    private Integer shareGive;

    @TableField("card_id")
    private String cardId;

    @TableField("stock_qty")
    private Integer stockQty;

    private Integer status;

    @TableField("refuse_reason")
    private String refuseReason;

    private String ext;

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
