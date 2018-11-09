package cn.siyue.platform.weixin.service.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import cn.siyue.platform.weixin.common.entity.WeixinMenu;
import cn.siyue.platform.weixin.common.request.weixinmenu.*;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 微信菜单表 服务类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
public interface WeixinMenuServiceContract extends IService<WeixinMenu> {

    public ResponseData addWeixinMenu(AddMenuRequest requestParam);

    public ResponseData updateWeixinMenu(UpdateMenuRequest requestParam);

    public ResponseData deleteWeixinMenu(DeleteMenuRequest requestParam);

    public ResponseData getWeixinMenuList(PageRequest requestParam);

    public ResponseData syncMenu(List<WeixinMenuButtonVo> list);

    public ResponseData moveUp(MoveUpRequest requestParam);

    public ResponseData moveDown(MoveDownRequest requestParam);

    public List<WeixinMenu> getMenuList();

    public ResponseData publish();


}
