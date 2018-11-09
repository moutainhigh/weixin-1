package cn.siyue.platform.weixin.common.dto.weixin.card.request.batchget;

import lombok.Data;

import java.util.List;

@Data
public class BatchGetReq {

    private Integer offset;
    private Integer count;
    private List<String> status_list;
}
