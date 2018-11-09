package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import lombok.Data;

@Data
public class PublishCardRequest {

    private Long id;
    private String cardId;
    private String coverMaterialUrl;
}
