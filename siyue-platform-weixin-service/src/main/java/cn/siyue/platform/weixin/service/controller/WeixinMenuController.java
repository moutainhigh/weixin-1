package cn.siyue.platform.weixin.service.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.common.common.weixinmenu.WeixinMenuButtonVo;
import cn.siyue.platform.weixin.common.request.weixinmenu.*;
import cn.siyue.platform.weixin.service.service.WeixinMenuServiceContract;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weixinMenu")
public class WeixinMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinMenuController.class);

    @Autowired
    private WeixinMenuServiceContract weixinMenuServiceContract;

    @ApiOperation(nickname = "addWeixinMenu", value = "新增微信菜单")
    @LogAnnotation
    @PostMapping("/addWeixinMenu")
    public ResponseData addWeixinMenu(@RequestBody AddMenuRequest requestParam) {
        try {
            return weixinMenuServiceContract.addWeixinMenu(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/deleteWeixinMenu")
    public ResponseData deleteWeixinMenu(@RequestBody DeleteMenuRequest requestParam) {
        try {
            return weixinMenuServiceContract.deleteWeixinMenu(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/updateWeixinMenu")
    public ResponseData updateWeixinMenu(@RequestBody UpdateMenuRequest requestParam) {
        try {
            return weixinMenuServiceContract.updateWeixinMenu(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/getWeixinMenuList")
    public ResponseData getWeixinMenuList(@RequestBody PageRequest requestParam) {
        try {
            return weixinMenuServiceContract.getWeixinMenuList(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/syncMenu")
    public ResponseData syncMenu(@RequestBody SyncMenuRequest requestParam) {
        try {
            return weixinMenuServiceContract.syncMenu(requestParam.getList());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/moveUp")
    public ResponseData moveUp(@RequestBody MoveUpRequest requestParam) {
        try {
            return weixinMenuServiceContract.moveUp(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/moveDown")
    public ResponseData moveDown(@RequestBody MoveDownRequest requestParam) {
        try {
            return weixinMenuServiceContract.moveDown(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @LogAnnotation
    @PostMapping("/publish")
    public ResponseData publish() {
        try {
            return weixinMenuServiceContract.publish();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }
}
