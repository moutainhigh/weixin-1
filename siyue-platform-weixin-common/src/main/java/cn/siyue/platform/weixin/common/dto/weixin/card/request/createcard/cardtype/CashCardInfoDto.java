package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype;

import lombok.Data;

@Data
public class CashCardInfoDto extends CardInfoDto {

    private Integer least_cost;
    private Integer reduce_cost;
}
