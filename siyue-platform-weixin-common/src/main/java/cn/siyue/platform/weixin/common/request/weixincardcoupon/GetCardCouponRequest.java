package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import lombok.Data;

@Data
public class GetCardCouponRequest {

    private Long id;

    private String cardId;
}
