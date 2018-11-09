package cn.siyue.platform.weixin.common.request.weixinmenu;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteMenuRequest {
    @NotNull(message = "id不能为空")
    private Long id;
}
