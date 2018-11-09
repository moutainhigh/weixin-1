package cn.siyue.platform.weixin.common.dto.weixin.card.request.getusercardlist;

import lombok.Data;

@Data
public class GetCardListReq {

    private String openid;
    private String card_id;
}
