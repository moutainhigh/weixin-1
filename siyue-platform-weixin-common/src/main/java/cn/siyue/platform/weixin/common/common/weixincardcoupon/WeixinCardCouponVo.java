package cn.siyue.platform.weixin.common.common.weixincardcoupon;

import cn.siyue.platform.weixin.common.dto.weixin.card.request.createcard.CreateCardReq;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.util.Date;

@Data
public class WeixinCardCouponVo extends CommonCardCouponVo {

    private Long id;

    private Integer status;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 更新时间
     */
    private Date updateAt;

    private String cardId;

    private String coverMaterialUrl;

    private String refuseReason;


}
