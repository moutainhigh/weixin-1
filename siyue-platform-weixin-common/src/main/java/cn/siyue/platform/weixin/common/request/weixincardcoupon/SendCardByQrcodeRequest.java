package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendCardByQrcodeRequest {

    @NotNull(message = "微信卡券id不能为空")
    private Long id;

    @NotNull(message = "openId不能为空")
    private String openId;
}
