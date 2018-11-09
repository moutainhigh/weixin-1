package cn.siyue.platform.weixin.common.dto.weixin.card.request.createcardqrcode;

import lombok.Data;

@Data
public class CreateCardQrcodeReq {
    private String action_name;

    private Integer expire_seconds;

    private ActionInfoDto action_info;

}
