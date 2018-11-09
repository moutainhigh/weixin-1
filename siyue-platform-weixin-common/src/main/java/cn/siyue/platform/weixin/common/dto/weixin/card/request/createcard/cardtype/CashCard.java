package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype;

import lombok.Data;

@Data
public class CashCard extends Card {

    private CashCardInfoDto cash;
}
