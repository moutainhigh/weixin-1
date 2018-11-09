package cn.siyue.platform.weixin.client.callback;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;

public class BaseServiceFallBack {
    protected ResponseData getDownGradeResponse() {
        return ResponseData.build(
                ResponseBackCode.ERROR_DOWNGRADE.getValue(),
                ResponseBackCode.ERROR_DOWNGRADE.getMessage()
        );
    }
}
