package cn.siyue.platform.weixin.common.service;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.weixin.common.request.weixinmenu.AddMenuRequest;
import cn.siyue.platform.weixin.common.request.weixinmenu.CreateMenuRequest;
import cn.siyue.platform.weixin.common.request.weixinmenu.UpdateMenuRequest;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

import java.util.List;

public interface MenuService {

    public ResponseData getMenu();

    public ResponseData createMenu(CreateMenuRequest requestParam);

    public ResponseData deleteMenu(String name, String parentName);

    public ResponseData addMenu(AddMenuRequest requestParam, String parentName, WxMpMenu wxMpMenu2);

    public ResponseData updateMenu(UpdateMenuRequest requestParam, String oldName, String oldParentName, String parentName);

    public WxMpMenu getWxMpMenu() throws WxErrorException;

    public ResponseData moveUp(String name, String parentName);

    public ResponseData moveDown(String name, String parentName);

    public ResponseData publishMenu(List<GetWeixinMenuListResponse > list);

}
