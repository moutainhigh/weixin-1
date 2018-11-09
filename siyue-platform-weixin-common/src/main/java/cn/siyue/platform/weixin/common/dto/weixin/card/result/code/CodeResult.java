package cn.siyue.platform.weixin.common.dto.weixin.card.result.code;

import lombok.Data;

@Data
public class CodeResult {

    private String openid;

    private Boolean can_consume;

    private String user_card_status;

    private Card card;


    @Data
    public static class Card {
        private String card_id;
        private Long begin_time;
        private Long end_time;
    }
}
