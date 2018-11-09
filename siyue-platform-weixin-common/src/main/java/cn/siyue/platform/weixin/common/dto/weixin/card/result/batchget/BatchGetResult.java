package cn.siyue.platform.weixin.common.dto.weixin.card.result.batchget;

import lombok.Data;

import java.util.List;

@Data
public class BatchGetResult {

    private List<String> card_id_list;

    private Integer total_num;
}
