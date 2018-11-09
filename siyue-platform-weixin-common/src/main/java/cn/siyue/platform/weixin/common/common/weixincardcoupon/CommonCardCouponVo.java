package cn.siyue.platform.weixin.common.common.weixincardcoupon;

import cn.siyue.platform.weixin.common.request.weixincardcoupon.TimeLimitVo;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CommonCardCouponVo {

    /**
     * 卡券任务id
     */
    @NotNull(message = "卡券任务id不能为空")
    @ApiModelProperty("卡券任务id")
    private Long taskId;
    /**
     * 卡券颜色
     */
    @NotNull(message = "卡券颜色不能为空")
    @ApiModelProperty("卡券颜色")
    private String color;
    /**
     * 封面图片url
     */
    @NotNull(message = "封面图片url不能为空")
    @ApiModelProperty("封面图片url")
    private String coverImageUrl;
    /**
     * 封面图片密钥
     */
    @NotNull(message = "封面图片密钥不能为空")
    @ApiModelProperty("封面图片密钥")
    private String coverImageSecret;
    /**
     * 封面简介
     */
    @ApiModelProperty("封面简介")
    private String coverIntro;
    /**
     * 使用须知
     */
    @ApiModelProperty("使用须知")
    private String useNotice;

    @ApiModelProperty("使用时间限制")
    private List<TimeLimitVo> timeLimitList;

    /**
     * center_title
     */
    @ApiModelProperty("center_title")
    private String centerTitle;
    /**
     * center_sub_title
     */
    @ApiModelProperty("center_sub_title")
    private String centerSubTitle;
    /**
     * center入口url
     */
    @ApiModelProperty("center入口url")
    private String centerUrl;
    /**
     * center入口appid
     */
    @ApiModelProperty("center入口appid")
    private String centerAppid;
    /**
     * center入口路径
     */
    @ApiModelProperty("center入口路径")
    private String centerPagePath;
    /**
     * 自定义入口名称
     */
    @ApiModelProperty("自定义入口名称")
    private String customEnterName;
    /**
     * 自定义入口引导语
     */
    @ApiModelProperty("自定义入口引导语")
    private String customEnterGuide;
    /**
     * 自定义入口URL
     */
    @ApiModelProperty("自定义入口URL")
    private String customEnterUrl;
    /**
     * 用户可以分享领券链接
     */
    @NotNull(message = "用户可以分享领券链接不能为空")
    @ApiModelProperty("用户可以分享领券链接")
    private Integer shareLink;
    /**
     * 用户领券后可转赠其他好友
     */
    @NotNull(message = "用户领券后可转赠其他好友不能为空")
    @ApiModelProperty("用户领券后可转赠其他好友")
    private Integer shareGive;

    @NotNull(message = "库存数量不能为空")
    @ApiModelProperty("库存数量")
    private Integer stockQty;

    private ObjectNode ext;
}
