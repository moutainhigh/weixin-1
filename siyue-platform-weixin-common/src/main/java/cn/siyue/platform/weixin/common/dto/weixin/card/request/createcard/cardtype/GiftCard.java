package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype;

import lombok.Data;

@Data
public class GiftCard extends Card {
    private GiftCardInfoDto gift;
}
