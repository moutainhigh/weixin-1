package cn.siyue.platform.weixin.common.request.weixinmenu;

import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import lombok.Data;

import java.util.List;

@Data
public class CreateMenuRequest {

    private List<WeixinMenuButtonVo> buttons;
}
