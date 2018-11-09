package cn.siyue.platform.weixin.common.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import cn.siyue.platform.weixin.common.config.WeixinConfig;
import cn.siyue.platform.weixin.common.request.weixinmenu.AddMenuRequest;
import cn.siyue.platform.weixin.common.request.weixinmenu.CreateMenuRequest;
import cn.siyue.platform.weixin.common.request.weixinmenu.UpdateMenuRequest;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetMenuListResponse;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.service.MenuService;
import cn.siyue.platform.weixin.common.service.WeixinMpService;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private WeixinConfig weixinConfig;

    @Autowired
    private WeixinMpService weixinMpService;

    @Override
    public ResponseData getMenu() {
        try {
            WxMpMenu wxMpMenu = getWxMpMenu();
            GetMenuListResponse resp = new GetMenuListResponse();
            if (wxMpMenu != null && wxMpMenu.getMenu() != null && wxMpMenu.getMenu().getButtons() != null && wxMpMenu.getMenu().getButtons().size() > 0) {
                List<WeixinMenuButtonVo> list = new ArrayList<WeixinMenuButtonVo>();
                for (WxMenuButton btn : wxMpMenu.getMenu().getButtons()) {
                    WeixinMenuButtonVo btnVo = new WeixinMenuButtonVo();
                    List<WxMenuButton> subBtnList = btn.getSubButtons();
                    btn.setSubButtons(null);
                    BeanUtils.copyProperties(btn, btnVo);

                    if (subBtnList != null && subBtnList.size() > 0) {
                        List<WeixinMenuButtonVo> subBtnVoList = new ArrayList<WeixinMenuButtonVo>();
                        for (WxMenuButton subBtn : subBtnList) {
                            WeixinMenuButtonVo subBtnVo = new WeixinMenuButtonVo();
                            BeanUtils.copyProperties(subBtn, subBtnVo);
                            subBtnVoList.add(subBtnVo);
                        }
                        btnVo.setSubButtons(subBtnVoList);
                    }

                    list.add(btnVo);
                }
                resp.setButtons(list);
            }
            return ResponseUtil.success(resp);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }
    }

    public WxMpMenu getWxMpMenu() throws WxErrorException {
        WxMpMenuService wxMpMenuService = weixinMpService.getWxMpService().getMenuService();
        WxMpMenu wxMpMenu = wxMpMenuService.menuGet();
        return wxMpMenu;

    }

    @Override
    public ResponseData moveUp(String name, String parentName) {
        WxMpMenu wxMpMenu = null;
        try {
            wxMpMenu = getWxMpMenu();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }

        if (wxMpMenu != null) {
            WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
            List<WxMenuButton> buttons = null;
            if (menu != null) {
                buttons = menu.getButtons();
            }
            if (buttons != null && buttons.size() > 0) {
                WxMenuButton preMenu = null;
                for (int i = 0; i < buttons.size(); i++) {
                    WxMenuButton btn = buttons.get(i);
                    //二级菜单
                    if (StringUtils.isNotEmpty(parentName)) {
                        if (btn.getName().equals(parentName)) {
                            List<WxMenuButton> subButtons = btn.getSubButtons();
                            if (subButtons != null && subButtons.size() > 0) {
                                for (int j = 0; j < subButtons.size(); j++) {
                                    WxMenuButton subBtn = subButtons.get(j);
                                    if (subBtn.getName().equals(name)) {
                                        if (preMenu == null) {
                                            return ResponseUtil.fail("已经是第一个");
                                        }
                                        subButtons.set(j - 1, subBtn);
                                        subButtons.set(j, preMenu);

                                        WxMenu wxMenu = new WxMenu();
                                        wxMenu.setButtons(buttons);
                                        try {
                                            createWxMenu(wxMenu);
                                            return ResponseUtil.success();
                                        } catch (WxErrorException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    preMenu = subBtn;

                                }
                            }
                        }
                    } else {

                        if (btn.getName().equals(name)) {
                            if (preMenu == null) {
                                return ResponseUtil.fail("已经是第一个");
                            }
                            buttons.set(i - 1, btn);
                            buttons.set(i, preMenu);

                            WxMenu wxMenu = new WxMenu();
                            wxMenu.setButtons(buttons);
                            try {
                                createWxMenu(wxMenu);
                                return ResponseUtil.success();
                            } catch (WxErrorException e) {
                                e.printStackTrace();
                            }
                        }
                        preMenu = btn;
                    }
                }
            }
        }

        return ResponseUtil.fail();
    }

    @Override
    public ResponseData moveDown(String name, String parentName) {
        WxMpMenu wxMpMenu = null;
        try {
            wxMpMenu = getWxMpMenu();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }

        if (wxMpMenu != null) {
            WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
            List<WxMenuButton> buttons = null;
            if (menu != null) {
                buttons = menu.getButtons();
            }
            if (buttons != null && buttons.size() > 0) {
                WxMenuButton curMenu = null;
                for (int i = 0; i < buttons.size(); i++) {
                    WxMenuButton btn = buttons.get(i);
                    //二级菜单
                    if (StringUtils.isNotEmpty(parentName)) {
                        if (btn.getName().equals(parentName)) {
                            List<WxMenuButton> subButtons = btn.getSubButtons();
                            if (subButtons != null && subButtons.size() > 0) {
                                for (int j = 0; j < subButtons.size(); j++) {
                                    WxMenuButton subBtn = subButtons.get(j);
                                    if (curMenu != null) {
                                        subButtons.set(j - 1, subBtn);
                                        subButtons.set(j, curMenu);

                                        WxMenu wxMenu = new WxMenu();
                                        wxMenu.setButtons(buttons);
                                        try {
                                            createWxMenu(wxMenu);
                                            return ResponseUtil.success();
                                        } catch (WxErrorException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (subBtn.getName().equals(name)) {
                                        curMenu = subBtn;

                                    }

                                }
                            }
                        }
                    } else {

                        if (curMenu != null) {
                            buttons.set(i - 1, btn);
                            buttons.set(i, curMenu);

                            WxMenu wxMenu = new WxMenu();
                            wxMenu.setButtons(buttons);
                            try {
                                createWxMenu(wxMenu);
                                return ResponseUtil.success();
                            } catch (WxErrorException e) {
                                e.printStackTrace();
                            }
                        }


                        if (btn.getName().equals(name)) {
                            curMenu = btn;
                        }
                    }
                }
            }
        }

        return ResponseUtil.fail();
    }

    @Override
    public ResponseData publishMenu(List<GetWeixinMenuListResponse > list) {
        if (list != null && list.size() > 0) {
            Map<Long, List<GetWeixinMenuListResponse>> map = new HashMap<Long, List<GetWeixinMenuListResponse>>();
            List<GetWeixinMenuListResponse> childrenList = null;
            for (GetWeixinMenuListResponse menu : list) {
                childrenList = map.get(menu.getParentId());
                if (childrenList == null) {
                    childrenList = new ArrayList<GetWeixinMenuListResponse>();
                    map.put(menu.getParentId(), childrenList);
                }
                childrenList.add(menu);
            }

            List<GetWeixinMenuListResponse> parentList = map.get(0L);
            if (parentList == null || parentList.size() == 0) {
                return ResponseUtil.fail("没有一级分类");
            }

            List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
            WxMenuButton parentBtn = null;
            WxMenuButton subBtn = null;
            for (GetWeixinMenuListResponse menu : parentList) {
                parentBtn = new WxMenuButton();
                BeanUtils.copyProperties(menu, parentBtn);
                parentBtn.setMediaId(null);
                buttons.add(parentBtn);
                childrenList = map.get(menu.getId());
                if (childrenList != null && childrenList.size() > 0) {
                    List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
                    parentBtn.setSubButtons(subButtons);
                    for (GetWeixinMenuListResponse subMenu : childrenList) {
                        subBtn = new WxMenuButton();
                        BeanUtils.copyProperties(subMenu, subBtn);
                        subBtn.setMediaId(null);
                        subButtons.add(subBtn);
                    }
                }
            }

            WxMenu wxMenu = new WxMenu();
            wxMenu.setButtons(buttons);
            try {
                createWxMenu(wxMenu);
                return ResponseUtil.success();
            } catch (WxErrorException e) {
                e.printStackTrace();
            }

        }
        return ResponseUtil.fail();
    }

    @Override
    public ResponseData createMenu(CreateMenuRequest requestParam) {
        try {
            WxMenu wxMenu = convertToWxMenu(requestParam);
            if (wxMenu != null) {
                String menuId = createWxMenu(wxMenu);
                return ResponseUtil.success(menuId);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();

        }
        return ResponseUtil.fail();
    }

    private WxMenu convertToWxMenu(CreateMenuRequest requestParam) {
        if (requestParam != null && requestParam.getButtons() != null && requestParam.getButtons().size() > 0) {
            WxMenu wxMenu = new WxMenu();
            List<WxMenuButton> btnList = new ArrayList<WxMenuButton>();
            for (WeixinMenuButtonVo btnVo : requestParam.getButtons()) {
                WxMenuButton btn = new WxMenuButton();
                List<WeixinMenuButtonVo> subBtnVoList = btnVo.getSubButtons();
                btnVo.setSubButtons(null);
                BeanUtils.copyProperties(btnVo, btn);
                btnList.add(btn);

                if (subBtnVoList != null && subBtnVoList.size() > 0) {
                    List<WxMenuButton> subBtnList = new ArrayList<WxMenuButton>();
                    for (WeixinMenuButtonVo subBtnVo : subBtnVoList) {
                        WxMenuButton subBtn = new WxMenuButton();
                        BeanUtils.copyProperties(subBtnVo, subBtn);
                        subBtnList.add(subBtn);
                    }
                    btn.setSubButtons(subBtnList);
                }
            }
            wxMenu.setButtons(btnList);
            return wxMenu;
        }
        return null;
    }

    private String createWxMenu(WxMenu wxMenu) throws WxErrorException {
        WxMpMenuService wxMpMenuService = weixinMpService.getWxMpService().getMenuService();
        String menuId = wxMpMenuService.menuCreate(wxMenu);
        return menuId;
    }

    @Override
    public ResponseData deleteMenu(String name, String parentName) {
        try {
            WxMpMenu wxMpMenu = getWxMpMenu();
            if (wxMpMenu != null) {
                WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
                List<WxMenuButton> buttons = null;
                if (menu != null) {
                    buttons = menu.getButtons();
                }
                if (buttons != null && buttons.size() > 0) {
                    WxMenuButton removedBtn = findAndRemoveMenuButton(buttons, name, parentName);
                    if (removedBtn != null) {
                        WxMenu wxMenu = new WxMenu();
                        wxMenu.setButtons(buttons);

                        createWxMenu(wxMenu);
                        return ResponseUtil.success();
                    }

                }

            }


        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }
        return ResponseUtil.fail();
    }

    @Override
    public ResponseData addMenu(AddMenuRequest requestParam, String parentName, WxMpMenu wxMpMenu2) {
        WxMpMenu wxMpMenu = null;
        if (wxMpMenu2 != null) {
            wxMpMenu = wxMpMenu2;
        } else {
            try {
                wxMpMenu = getWxMpMenu();
            } catch (WxErrorException e) {
                e.printStackTrace();
                return ResponseUtil.fail();
            }
        }
        if (wxMpMenu != null) {
            WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
            List<WxMenuButton> buttons = null;
            if (menu != null) {
                buttons = menu.getButtons();
            }

            // 二级菜单
            if (StringUtils.isNotEmpty(parentName)) {
                WxMenuButton parentMenu = findMenuButton(buttons, parentName, null);
                if (parentMenu == null) {
                    return ResponseUtil.fail("找不到父菜单");
                }
                WxMenuButton addBtn = new WxMenuButton();
                BeanUtils.copyProperties(requestParam, addBtn);
                if (parentMenu.getSubButtons() == null) {
                    List<WxMenuButton> btnList = new ArrayList<WxMenuButton>();
                    parentMenu.setSubButtons(btnList);
                }
                parentMenu.getSubButtons().add(addBtn);

            } else { // 一级菜单
                WxMenuButton addBtn = new WxMenuButton();
                BeanUtils.copyProperties(requestParam, addBtn);
                if (menu == null) {
                    menu = new WxMpMenu.WxMpConditionalMenu();
                    wxMpMenu.setMenu(menu);
                }

                if (buttons == null) {
                    buttons = new ArrayList<WxMenuButton>();
                    menu.setButtons(buttons);
                }

                buttons.add(addBtn);

            }
            WxMenu wxMenu = new WxMenu();
            wxMenu.setButtons(buttons);
            try {
                createWxMenu(wxMenu);
                return ResponseUtil.success();
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
        return ResponseUtil.fail();
    }

    @Override
    public ResponseData updateMenu(UpdateMenuRequest requestParam, String oldName, String oldParentName, String parentName) {
        WxMpMenu wxMpMenu = null;
        try {
            wxMpMenu = getWxMpMenu();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }
        if (wxMpMenu != null) {
            WxMpMenu.WxMpConditionalMenu menu = wxMpMenu.getMenu();
            if (menu != null) {
                buildMenu(menu, requestParam, oldName, oldParentName, parentName);

                WxMenu wxMenu = new WxMenu();
                wxMenu.setButtons(menu.getButtons());
                try {
                    createWxMenu(wxMenu);
                    return ResponseUtil.success();
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }

            }
        }
        return ResponseUtil.fail();
    }

    private void buildMenu(WxMpMenu.WxMpConditionalMenu menu, UpdateMenuRequest requestParam, String oldName, String oldParentName, String parentName) {
        List<WxMenuButton> buttons = menu.getButtons();
        if (buttons == null) {
            buttons = new ArrayList<WxMenuButton>();
            menu.setButtons(buttons);
        }

        for (int i = 0; i < buttons.size(); i++) {
            WxMenuButton btn = buttons.get(i);
            // 原菜单属于二级菜单
            if (StringUtils.isNotEmpty(oldParentName)) {
                if (btn.getName().equals(oldParentName)) {
                    if (btn.getSubButtons() != null && btn.getSubButtons().size() > 0) {
                        for (int j = 0; j < btn.getSubButtons().size(); j++) {
                            WxMenuButton subBtn = btn.getSubButtons().get(j);
                            if (subBtn.getName().equals(oldName)) {
                                // 父级菜单没有改变
                                if (oldParentName.equals(parentName)) {
                                    BeanUtils.copyProperties(requestParam, subBtn);
                                } else {
                                    btn.getSubButtons().remove(j);
                                    BeanUtils.copyProperties(requestParam, subBtn);

                                    // 新菜单属于二级菜单
                                    if (StringUtils.isNotEmpty(parentName)) {
                                        WxMenuButton parentBtn = findMenuButton(buttons, parentName, null);
                                        List<WxMenuButton> newSubButtons = parentBtn.getSubButtons();
                                        if (newSubButtons == null) {
                                            newSubButtons = new ArrayList<WxMenuButton>();
                                            parentBtn.setSubButtons(newSubButtons);
                                        }
                                        newSubButtons.add(subBtn);
                                    } else {
                                        buttons.add(subBtn);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                if (btn.getName().equals(oldName)) {
                    // 新菜单是二级菜单
                    if (StringUtils.isNotEmpty(parentName)) {
                        buttons.remove(i);
                        BeanUtils.copyProperties(requestParam, btn);
                        WxMenuButton parentBtn = findMenuButton(buttons, parentName, null);
                        List<WxMenuButton> newSubButtons = parentBtn.getSubButtons();
                        if (newSubButtons == null) {
                            newSubButtons = new ArrayList<WxMenuButton>();
                            parentBtn.setSubButtons(newSubButtons);
                        }
                        newSubButtons.add(btn);
                    } else { // 新菜单也是一级菜单
                        BeanUtils.copyProperties(requestParam, btn);
                    }

                    break;
                }
            }
        }
    }

    private WxMenuButton findAndRemoveMenuButton(List<WxMenuButton> buttons, String btnName, String parentName) {
        if (buttons != null && buttons.size() > 0) {
            for (int i = 0; i < buttons.size(); i++) {
                WxMenuButton btn = buttons.get(i);
                // 从二级菜单找
                if (StringUtils.isNotEmpty(parentName)) {
                    if (btn.getName().equals(parentName)) {
                        if (btn.getSubButtons() != null && btn.getSubButtons().size() > 0) {
                            for (int j = 0; j < btn.getSubButtons().size(); j++) {
                                WxMenuButton subBtn = btn.getSubButtons().get(j);
                                if (subBtn.getName().equals(btnName)) {
                                    btn.getSubButtons().remove(j);
                                    return subBtn;
                                }
                            }
                        }
                    }
                } else {
                    if (btn.getName().equals(btnName)) {
                        buttons.remove(i);
                        return btn;
                    }
                }
            }
        }
        return null;
    }


    private WxMenuButton findMenuButton(List<WxMenuButton> buttons, String btnName, String parentName) {
        if (buttons != null && buttons.size() > 0) {
            for (WxMenuButton btn : buttons) {
                // 从二级菜单找
                if (StringUtils.isNotEmpty(parentName)) {
                    if (btn.getName().equals(parentName)) {
                        if (btn.getSubButtons() != null && btn.getSubButtons().size() > 0) {
                            for (WxMenuButton subBtn : btn.getSubButtons()) {
                                if (subBtn.getName().equals(btnName))
                                    return subBtn;
                            }
                        }
                    }
                } else {
                    if (btn.getName().equals(btnName)) {
                        return btn;
                    }
                }
            }
        }
        return null;
    }

    private void deleteWxMenu() throws WxErrorException {
        WxMpMenuService wxMpMenuService = weixinMpService.getWxMpService().getMenuService();
        wxMpMenuService.menuDelete();

    }


}
