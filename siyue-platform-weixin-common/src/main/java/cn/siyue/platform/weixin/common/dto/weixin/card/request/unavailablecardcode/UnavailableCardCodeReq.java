package cn.siyue.platform.weixin.common.dto.weixin.card.request.unavailablecardcode;

import lombok.Data;

@Data
public class UnavailableCardCodeReq {

    private String card_id;

    private String code;

    private String reason;
}
