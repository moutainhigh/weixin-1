package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import cn.siyue.platform.weixin.common.common.PageRequest;
import lombok.Data;

@Data
public class SearchCardCouponRequest extends PageRequest {
    private String name;
    private Integer typeId;
    private Integer status;
}
