package cn.siyue.platform.weixin.common.dto.weixin.card.request.setselfconsumecell;

import lombok.Data;

@Data
public class SetSelfConsumeCellReq {

    private String card_id;
    private Boolean is_open;
    /*private Boolean need_verify_cod;
    private Boolean need_remark_amount;*/

}
