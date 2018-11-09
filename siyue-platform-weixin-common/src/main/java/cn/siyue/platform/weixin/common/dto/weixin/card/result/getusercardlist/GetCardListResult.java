package cn.siyue.platform.weixin.common.dto.weixin.card.result.getusercardlist;

import lombok.Data;

import java.util.List;

@Data
public class GetCardListResult {

    private List<UserCardInfo> card_list;

    private Boolean has_share_card;


    @Data
    public static class UserCardInfo {
        private String code;

        private String card_id;
    }

}
