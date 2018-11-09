package cn.siyue.platform.weixin.common.request.weixinmenu;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MoveUpRequest {

    @NotNull(message = "id不能为空")
    private Long id;
}
