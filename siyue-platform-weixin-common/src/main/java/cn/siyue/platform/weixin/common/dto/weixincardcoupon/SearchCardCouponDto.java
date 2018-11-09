package cn.siyue.platform.weixin.common.dto.weixincardcoupon;

import cn.siyue.platform.weixin.common.entity.WeixinCardCoupon;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchCardCouponDto extends WeixinCardCoupon {

    @ApiModelProperty("微信卡券名称")
    private String name;
    @ApiModelProperty("类型: 1-代金券, 2-折扣券, 3-赠品券")
    private Integer typeId;
    @ApiModelProperty("有效开始时间")
    private LocalDateTime validTimeStartAt;
    @ApiModelProperty("有效结束时间")
    private LocalDateTime validTimeEndAt;
    @ApiModelProperty("从第几天起有效，0表示当天有效")
    private Integer receiptStartDay;
    @ApiModelProperty("有效后结束天数")
    private Integer receiptEndDay;
}
