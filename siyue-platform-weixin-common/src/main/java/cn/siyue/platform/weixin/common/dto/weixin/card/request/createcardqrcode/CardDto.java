package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode;

import lombok.Data;

@Data
public class CardDto {

    private String card_id;

    private String code;

    private String openid;

    private Boolean is_unique_code;

    private String outer_str;
}
