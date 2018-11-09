package cn.siyue.platform.weixin.common.dto.weixin.card.result.createcardqrcode;

import lombok.Data;

@Data
public class CreateCardQrcodeResult {

    private String ticket;

    private String url;

    private String show_qrcode_url;
}
