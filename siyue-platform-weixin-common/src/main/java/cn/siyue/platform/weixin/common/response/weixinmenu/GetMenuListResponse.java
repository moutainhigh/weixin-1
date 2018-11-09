package cn.siyue.platform.weixin.common.response.weixinmenu;

import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import lombok.Data;

import java.util.List;

@Data
public class GetMenuListResponse {
    private List<WeixinMenuButtonVo> buttons;
}
