package cn.siyue.platform.weixin.common.request.weixincardcoupon;

import lombok.Data;

@Data
public class TimeLimitVo {

    private String type;
    private int begin_hour;
    private int end_hour;
    private int begin_minute;
    private int end_minute;
}
