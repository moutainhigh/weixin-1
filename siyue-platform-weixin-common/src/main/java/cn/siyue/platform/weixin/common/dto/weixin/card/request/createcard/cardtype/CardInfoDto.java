package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.cardtype;

import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.advancedinfo.Advanced_info;
import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.baseinfo.Base_info;
import lombok.Data;

@Data
public class CardInfoDto {

    private Base_info base_info;
    private Advanced_info advanced_info;
}
