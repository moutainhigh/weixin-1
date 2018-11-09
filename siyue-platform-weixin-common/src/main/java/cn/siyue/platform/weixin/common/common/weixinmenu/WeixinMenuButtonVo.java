package cn.siyue.platform.weixin.common.common.weixinmenu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeixinMenuButtonVo extends BaseWeixinMenuButtonVo {


    private List<WeixinMenuButtonVo> subButtons = new ArrayList<>();
}
