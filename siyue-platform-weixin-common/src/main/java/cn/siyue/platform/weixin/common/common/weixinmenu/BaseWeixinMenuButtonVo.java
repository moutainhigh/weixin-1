package cn.siyue.platform.weixin.common.common.weixinmenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BaseWeixinMenuButtonVo {
    /**
     * <pre>
     * 菜单的响应动作类型.
     * view表示网页类型，
     * click表示点击类型，
     * miniprogram表示小程序类型
     * </pre>
     */
    @ApiModelProperty("类型: click-消息推送，view-url, miniprogram-小程序")
    private String type;

    /**
     * 菜单标题，不超过16个字节，子菜单不超过60个字节.
     */
    @ApiModelProperty("菜单标题")
    @NotNull(message = "菜单标题不能为空")
    private String name;

    /**
     * <pre>
     * 菜单KEY值，用于消息接口推送，不超过128字节.
     * click等点击类型必须
     * </pre>
     */
    @ApiModelProperty("菜单KEY值, 消息推送类型必填")
    private String key;

    /**
     * <pre>
     * 网页链接.
     * 用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。
     * view、miniprogram类型必须
     * </pre>
     */
    @ApiModelProperty("用户点击菜单可打开链接, url类型必填")
    private String url;

    /**
     * <pre>
     * 调用新增永久素材接口返回的合法media_id.
     * media_id类型和view_limited类型必须
     * </pre>
     */
    private String mediaId;

    /**
     * <pre>
     * 小程序的appid.
     * miniprogram类型必须
     * </pre>
     */
    @ApiModelProperty("小程序的appid")
    private String appId;

    /**
     * <pre>
     * 小程序的页面路径.
     * miniprogram类型必须
     * </pre>
     */
    @ApiModelProperty("小程序的页面路径")
    private String pagePath;
}
