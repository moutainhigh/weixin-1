package cn.siyue.platform.weixin.common.request.material;

import cn.siyue.platform.weixin.common.common.PageRequest;
import lombok.Data;

@Data
public class GetMaterialListRequest extends PageRequest {

    private String type;
}
