package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import cn.siyue.platform.weixin.common.common.weixincardcoupon.CommonCardCouponVo;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype.Card;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCardCouponCommonRequest extends CommonCardCouponVo {

    @NotNull(message = "微信卡券id不能为空")
    private Long id;

}
