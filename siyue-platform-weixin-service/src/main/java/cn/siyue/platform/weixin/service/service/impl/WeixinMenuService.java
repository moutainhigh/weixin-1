package cn.siyue.platform.weixin.service.service.impl;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import cn.siyue.platform.weixin.common.entity.WeixinMenu;
import cn.siyue.platform.weixin.common.entity.WeixinMenuOnline;
import cn.siyue.platform.weixin.common.entity.WeixinMessageReply;
import cn.siyue.platform.weixin.common.request.weixinmenu.*;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.response.weixinmenuonline.WeixinMenuOnlineVo;
import cn.siyue.platform.weixin.common.response.weixinmessagereply.WeixinMessageReplyResponse;
import cn.siyue.platform.weixin.common.service.MenuService;
import cn.siyue.platform.weixin.service.mapper.WeixinMenuMapper;
import cn.siyue.platform.weixin.service.service.WeixinMenuOnlineServiceContract;
import cn.siyue.platform.weixin.service.service.WeixinMenuServiceContract;
import cn.siyue.platform.weixin.service.util.ConvertUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 微信菜单表 服务实现类
 * </p>
 *
 * @author Sipin ERP Development Team
 */
@Primary
@Service
public class WeixinMenuService extends ServiceImpl<WeixinMenuMapper, WeixinMenu> implements WeixinMenuServiceContract {

    @Autowired
    private MenuService menuService;

    @Autowired
    private WeixinMenuOnlineServiceContract weixinMenuOnlineServiceContract;

    private Integer getNextSort(Long parentId) {
        EntityWrapper<WeixinMenu> entityWrapper = new EntityWrapper<WeixinMenu>();
        entityWrapper.eq("parent_id", parentId);
        entityWrapper.orderBy("sort", false);
        WeixinMenu lastWeixinMenu = selectOne(entityWrapper);
        if (lastWeixinMenu == null)
            return 1;

        Integer nextSort = lastWeixinMenu.getSort() + 1;
        return nextSort;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData addWeixinMenu(AddMenuRequest requestParam) {
        WeixinMenu parentMenu = null;
        if (requestParam.getParentId() != null && requestParam.getParentId() != 0) {
            parentMenu = selectById(requestParam.getParentId());
            if (parentMenu == null) {
                return ResponseUtil.fail("找不到父id");
            }
        }

        Integer sort = getNextSort(requestParam.getParentId());

        WeixinMenu weixinMenu = new WeixinMenu();
        BeanUtils.copyProperties(requestParam, weixinMenu);
        Date now = new Date();
        weixinMenu.setSort(sort);
        weixinMenu.setCreateAt(now);
        weixinMenu.setUpdateAt(now);
        insertAllColumn(weixinMenu);
        return ResponseUtil.success();

        /*String parentName = null;
        if (parentMenu != null) {
            parentName = parentMenu.getName();
        }

        ResponseData weixinResp = menuService.addMenu(requestParam, parentName, null);
        if (ResponseBackCode.SUCCESS.getValue() != weixinResp.getCode()) {
            throw new RuntimeException();
        }
        return weixinResp;*/
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData updateWeixinMenu(UpdateMenuRequest requestParam) {
        /*String parentName = null;
        WeixinMenu parentMenu = null;
        if (requestParam.getParentId() != null && requestParam.getParentId() != 0) {
            parentMenu = selectById(requestParam.getParentId());
            if (parentMenu == null) {
                return ResponseUtil.fail("找不到父id");
            }
            parentName = parentMenu.getName();
        }*/

        WeixinMenu weixinMenu = selectById(requestParam.getId());

        /*String oldParentName = null;
        if (weixinMenu.getParentId() != null) {
            WeixinMenu oldParentWeixinMenu = selectById(weixinMenu.getParentId());
            oldParentName = oldParentWeixinMenu.getName();
        }*/

        BeanUtils.copyProperties(requestParam, weixinMenu);
        Date now = new Date();
        weixinMenu.setUpdateAt(now);
        updateAllColumnById(weixinMenu);

        return ResponseUtil.success();

        /*ResponseData weixinResp = menuService.updateMenu(requestParam, weixinMenu.getName(), oldParentName, parentName);
        if (ResponseBackCode.SUCCESS.getValue() != weixinResp.getCode()) {
            throw new RuntimeException();
        }
        return weixinResp;*/
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deleteWeixinMenu(DeleteMenuRequest requestParam) {
        /*WeixinMenu weixinMenu = selectById(requestParam.getId());
        String parentName = null;
        if (weixinMenu.getParentId() != null && weixinMenu.getParentId() != 0) {
            WeixinMenu parentWeixinMenu = selectById(weixinMenu.getParentId());
            if (parentWeixinMenu != null) {
                parentName = parentWeixinMenu.getName();
            }
        }*/

        deleteById(requestParam.getId());
        return ResponseUtil.success();
        /*ResponseData weixinResp = menuService.deleteMenu(weixinMenu.getName(), parentName);
        if (ResponseBackCode.SUCCESS.getValue() != weixinResp.getCode()) {
            throw new RuntimeException();
        }
        return weixinResp;*/
    }

    @Override
    public ResponseData getWeixinMenuList(PageRequest requestParam) {
        EntityWrapper<WeixinMenu> entityWrapper = new EntityWrapper<WeixinMenu>();
        entityWrapper.orderBy("parent_id");
        entityWrapper.orderBy("sort");
        Page<WeixinMenu> page = new Page<WeixinMenu>(requestParam.getPage(), requestParam.getSize());
        Page<WeixinMenu> pageResult = selectPage(page, entityWrapper);
        if (pageResult != null) {
            List<WeixinMenu> list = pageResult.getRecords();

            try {
                List<GetWeixinMenuListResponse> resultList = ConvertUtil.convertList(GetWeixinMenuListResponse.class, list);
                PageResponse<GetWeixinMenuListResponse> pageResponse = ConvertUtil.getPageResponse(pageResult, resultList);
                return ResponseUtil.success(pageResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return ResponseUtil.fail();
    }

    private Long addWeixinMenu(WeixinMenuButtonVo btn, Long parentId, Integer sort) {
        WeixinMenu weixinMenu = new WeixinMenu();
        BeanUtils.copyProperties(btn, weixinMenu);
        Date now = new Date();
        weixinMenu.setCreateAt(now);
        weixinMenu.setUpdateAt(now);
        weixinMenu.setParentId(parentId);
        weixinMenu.setSort(sort);
        insertAllColumn(weixinMenu);

        weixinMenuOnlineServiceContract.add(weixinMenu);
        return weixinMenu.getId();
    }

    @Override
    public ResponseData syncMenu(List<WeixinMenuButtonVo> list) {
        if (list != null && list.size() > 0) {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPage(1);
            pageRequest.setSize(1);
            ResponseData menuListData = weixinMenuOnlineServiceContract.getList(pageRequest);
            if (menuListData != null && ResponseBackCode.SUCCESS.getValue() == menuListData.getCode()) {
                PageResponse<WeixinMenuOnlineVo> pageResp = (PageResponse<WeixinMenuOnlineVo>)menuListData.getData();
                if (pageResp != null && pageResp.getRecords() != null && pageResp.getRecords().size() > 0) {
                    return ResponseUtil.fail("已经同步过了");
                }

            } else {
                return ResponseUtil.fail("查询微信菜单列表出现异常");
            }

            Integer oneSort = 1;
            for (WeixinMenuButtonVo btn : list) {
                Long menuId = addWeixinMenu(btn, 0L, oneSort++);

                if (btn.getSubButtons() != null && btn.getSubButtons().size() > 0) {
                    Integer twoSort = 1;
                    for (WeixinMenuButtonVo subBtn : btn.getSubButtons()) {
                        addWeixinMenu(subBtn, menuId, twoSort++);
                    }
                }
            }
            return ResponseUtil.success();
        } else {
            return ResponseUtil.fail("没有数据需要同步");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData moveUp(MoveUpRequest requestParam) {
        WeixinMenu weixinMenu = selectById(requestParam.getId());
        Long parentId = weixinMenu.getParentId();
        EntityWrapper<WeixinMenu> entityWrapper = new EntityWrapper<WeixinMenu>();
        entityWrapper.eq("parent_id", parentId);
        entityWrapper.orderBy("sort");
        List<WeixinMenu> list = selectList(entityWrapper);
        if (list != null && list.size() > 0) {
            WeixinMenu preMenu = null;
            for (int i = 0; i < list.size(); i++) {
                WeixinMenu menu = list.get(i);
                if (menu.getId().equals(requestParam.getId())) {
                    if (preMenu == null) {
                        return ResponseUtil.fail("已经是第一个");
                    }

                    Integer preSort = preMenu.getSort();
                    preMenu.setSort(menu.getSort());
                    Date now = new Date();
                    preMenu.setUpdateAt(now);
                    menu.setSort(preSort);
                    menu.setUpdateAt(now);
                    updateAllColumnById(preMenu);
                    updateAllColumnById(menu);
                    break;
                }
                preMenu = menu;
            }
        }
        return ResponseUtil.success();

        /*String parentName = null;
        if (weixinMenu.getParentId() != null) {
            WeixinMenu parentWeixinMenu = selectById(weixinMenu.getParentId());
            if (parentWeixinMenu != null) {
                parentName = parentWeixinMenu.getName();
            }
        }
        ResponseData weixinResp = menuService.moveUp(weixinMenu.getName(), parentName);
        if (ResponseBackCode.SUCCESS.getValue() != weixinResp.getCode()) {
            throw new RuntimeException();
        }
        return weixinResp;*/

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData moveDown(MoveDownRequest requestParam) {
        WeixinMenu weixinMenu = selectById(requestParam.getId());
        Long parentId = weixinMenu.getParentId();
        EntityWrapper<WeixinMenu> entityWrapper = new EntityWrapper<WeixinMenu>();
        entityWrapper.eq("parent_id", parentId);
        entityWrapper.orderBy("sort");
        List<WeixinMenu> list = selectList(entityWrapper);
        if (list != null && list.size() > 0) {
            WeixinMenu curMenu = null;
            for (int i = 0; i < list.size(); i++) {
                WeixinMenu menu = list.get(i);

                if (curMenu != null) {
                    Integer curSort = curMenu.getSort();
                    curMenu.setSort(menu.getSort());
                    Date now = new Date();
                    curMenu.setUpdateAt(now);
                    menu.setSort(curSort);
                    menu.setUpdateAt(now);
                    updateAllColumnById(curMenu);
                    updateAllColumnById(menu);
                    break;
                }

                if (menu.getId().equals(requestParam.getId())) {
                    curMenu = menu;
                }


            }
        }

        return ResponseUtil.success();

        /*String parentName = null;
        if (weixinMenu.getParentId() != null) {
            WeixinMenu parentWeixinMenu = selectById(weixinMenu.getParentId());
            if (parentWeixinMenu != null) {
                parentName = parentWeixinMenu.getName();
            }
        }
        ResponseData weixinResp = menuService.moveDown(weixinMenu.getName(), parentName);
        if (ResponseBackCode.SUCCESS.getValue() != weixinResp.getCode()) {
            throw new RuntimeException();
        }
        return weixinResp;*/
    }

    @Override
    public List<WeixinMenu> getMenuList() {
        return selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData publish() {
        weixinMenuOnlineServiceContract.deleteAll();

        List<WeixinMenu> menuList = getMenuList();
        if (menuList != null && menuList.size() > 0) {
            List<WeixinMenuOnline> entityList = new ArrayList<WeixinMenuOnline>();

            for (WeixinMenu menu : menuList) {
                WeixinMenuOnline entity = new WeixinMenuOnline();
                BeanUtils.copyProperties(menu, entity);
                entityList.add(entity);
            }
            weixinMenuOnlineServiceContract.insertBatch(entityList);
        }
        return ResponseUtil.success();
    }


}
