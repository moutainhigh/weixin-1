package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import lombok.Data;

@Data
public class AddCardCouponRequest extends AddCardCouponCommonRequest {
    private Integer taskTypeId;

}
