package cn.siyue.platform.weixin.common.dto.weixin.card.request.consumecode;

import lombok.Data;

@Data
public class ConsumeCodeReq {

    private String card_id;

    private String code;
}
