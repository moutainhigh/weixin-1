package cn.siyue.platform.weixin.common.request.weixinmenu;

import cn.siyue.platform.weixin.common.common.weixinmenu.BaseWeixinMenuButtonVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommonMenuRequest extends BaseWeixinMenuButtonVo {
    @ApiModelProperty("父id,一级菜单为0")
    @NotNull(message = "父id不能为空")
    private Long parentId;
}
