package cn.siyue.platform.weixin.client.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.constants.ResponseBackCode;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.client.service.MenuRedisService;
import cn.siyue.platform.weixin.client.service.weixin.WeixinMenuClientService;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.PageResponse;
import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import cn.siyue.platform.weixin.common.constants.WeixinConstant;
import cn.siyue.platform.weixin.common.request.weixinmenu.*;
import cn.siyue.platform.weixin.common.response.weixinmenu.GetWeixinMenuListResponse;
import cn.siyue.platform.weixin.common.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "微信_菜单接口")
@RestController
@RequestMapping("/weixinMenu")
public class WeixinMenuController extends BaseController {

    @Autowired
    private WeixinMenuClientService weixinMenuClientService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRedisService menuRedisService;

    @LogAnnotation
    @ApiOperation(nickname = "getMenuList",value = "得到菜单列表")
    @GetMapping("/getMenuList")
    public ResponseData<PageResponse<GetWeixinMenuListResponse>> getMenuList(@Valid PageRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        return weixinMenuClientService.getWeixinMenuList(requestParam);
    }

    @LogAnnotation
    @ApiOperation(nickname = "syncMenu",value = "同步菜单")
    @PostMapping("/syncMenu")
    public ResponseData syncMenu() {
        WxMpMenu wxMpMenu = null;
        try {
            wxMpMenu = menuService.getWxMpMenu();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ResponseUtil.fail();
        }

        if (wxMpMenu != null && wxMpMenu.getMenu() != null && wxMpMenu.getMenu().getButtons().size() > 0) {
            List<WeixinMenuButtonVo> btnList = new ArrayList<WeixinMenuButtonVo>();
            for (WxMenuButton btn : wxMpMenu.getMenu().getButtons()) {
                WeixinMenuButtonVo oneVo = new WeixinMenuButtonVo();
                BeanUtils.copyProperties(btn, oneVo);
                btnList.add(oneVo);
                if (btn.getSubButtons() != null && btn.getSubButtons().size() > 0) {
                    List<WeixinMenuButtonVo> twoList = new ArrayList<WeixinMenuButtonVo>();
                    oneVo.setSubButtons(twoList);
                    for (WxMenuButton twoBtn : btn.getSubButtons()) {
                        WeixinMenuButtonVo twoVo = new WeixinMenuButtonVo();
                        BeanUtils.copyProperties(twoBtn, twoVo);
                        twoList.add(twoVo);
                    }
                }
            }
            SyncMenuRequest requestParam = new SyncMenuRequest();
            requestParam.setList(btnList);
            return weixinMenuClientService.syncMenu(requestParam);
        }
        return ResponseUtil.fail();

    }

    @LogAnnotation
    @ApiOperation(nickname = "addMenu",value = "新增菜单")
    @PostMapping("/addMenu")
    public ResponseData addMenu(@Valid @RequestBody AddMenuRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        ResponseData respData = weixinMenuClientService.addWeixinMenu(requestParam);
        return respData;

    }

    private void invalidateCache(ResponseData respData) {
        if (respData != null && ResponseBackCode.SUCCESS.getValue() == respData.getCode()) {
            menuRedisService.delMenuList();
        }
    }

    @LogAnnotation
    @ApiOperation(nickname = "updateMenu",value = "修改菜单")
    @PostMapping("/updateMenu")
    public ResponseData updateMenu(@Valid @RequestBody UpdateMenuRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        ResponseData respData = weixinMenuClientService.updateWeixinMenu(requestParam);
        return respData;
    }

    @LogAnnotation
    @ApiOperation(nickname = "deleteMenu",value = "删除菜单")
    @PostMapping("/deleteMenu")
    public ResponseData deleteMenu(@Valid @RequestBody DeleteMenuRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        ResponseData respData = weixinMenuClientService.deleteWeixinMenu(requestParam);
        return respData;
    }

    @LogAnnotation
    @ApiOperation(nickname = "moveUp",value = "上移菜单")
    @PostMapping("/moveUp")
    public ResponseData moveUp(@Valid @RequestBody MoveUpRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        return weixinMenuClientService.moveUp(requestParam);
    }

    @LogAnnotation
    @ApiOperation(nickname = "moveDown",value = "下移菜单")
    @PostMapping("/moveDown")
    public ResponseData moveDown(@Valid @RequestBody MoveDownRequest requestParam, BindingResult result) {
        //请求的数据参数格式不正确
        if (result.hasErrors()) {
            return getErrorResponse(result);
        }

        return weixinMenuClientService.moveDown(requestParam);
    }

    @LogAnnotation
    @ApiOperation(nickname = "publishMenu",value = "发布菜单")
    @PostMapping("/publishMenu")
    public ResponseData publishMenu() {
        PageRequest requestParam = new PageRequest();
        requestParam.setPage(WeixinConstant.PAGE_ONE);
        requestParam.setSize(WeixinConstant.PAGE_SIZE_ALL);
        ResponseData<PageResponse<GetWeixinMenuListResponse>> respData = weixinMenuClientService.getWeixinMenuList(requestParam);

        if (ResponseBackCode.SUCCESS.getValue() != respData.getCode()) {
            return ResponseUtil.build(respData.getCode(), respData.getMsg());
        }

        if (respData != null && respData.getData() != null) {
            PageResponse<GetWeixinMenuListResponse> pageResp = (PageResponse<GetWeixinMenuListResponse>)respData.getData();
            if (pageResp != null && pageResp.getRecords() != null && pageResp.getRecords().size() > 0) {
                ResponseData menuRespData = menuService.publishMenu(pageResp.getRecords());
                if (ResponseUtil.isSuccess(menuRespData)) {
                    ResponseData pubRespData = weixinMenuClientService.publish();
                    if (ResponseUtil.isSuccess(pubRespData)) {
                        invalidateCache(pubRespData);
                    }
                    return pubRespData;
                }
            }
        }

        return ResponseUtil.fail();
    }

}
