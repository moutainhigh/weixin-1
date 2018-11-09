package cn.siyue.platform.weixin.common.dto.weixin.card.request.modifystock;

import lombok.Data;

@Data
public class ModifyStockReq {

    private String card_id;

    private Integer increase_stock_value;

    private Integer reduce_stock_value;
}
