package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype;

import lombok.Data;

@Data
public class MemberCard extends Card {

    private MemberCardInfoDto member_card;
}
