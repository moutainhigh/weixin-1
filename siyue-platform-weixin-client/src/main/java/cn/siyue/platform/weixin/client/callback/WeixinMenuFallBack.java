package cn.siyue.platform.weixin.client.callback;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMenuClientService;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.request.weixinmenu.*;
import org.springframework.stereotype.Component;

@Component
public class WeixinMenuFallBack extends BaseServiceFallBack implements WeixinMenuClientService {


    @Override
    public ResponseData addWeixinMenu(AddMenuRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData updateWeixinMenu(UpdateMenuRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData deleteWeixinMenu(DeleteMenuRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData getWeixinMenuList(PageRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData syncMenu(SyncMenuRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData moveUp(MoveUpRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData moveDown(MoveDownRequest requestParam) {
        return getDownGradeResponse();
    }

    @Override
    public ResponseData publish() {
        return getDownGradeResponse();
    }

}
