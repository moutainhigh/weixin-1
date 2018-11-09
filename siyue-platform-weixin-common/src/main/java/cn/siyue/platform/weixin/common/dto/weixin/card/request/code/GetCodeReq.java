package cn.siyue.platform.weixin.common.dto.weixin.card.request.code;

import lombok.Data;

@Data
public class GetCodeReq {
    private String card_id;

    private String code;

    private Boolean check_consume;
}
