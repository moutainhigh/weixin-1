package cn.siyue.platform.weixin.service.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.entity.WeixinCardCoupon;
import cn.siyue.platform.weixin.common.request.weixincardcoupon.*;
import cn.siyue.platform.weixin.service.service.WeixinCardCouponServiceContract;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/weixinCardCoupon")
public class WeixinCardCouponController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinCardCouponController.class);

    @Autowired
    private WeixinCardCouponServiceContract weixinCardCouponServiceContract;

    @ApiOperation(nickname = "add", value = "新增微信卡券")
    @LogAnnotation
    @PostMapping("/add")
    public ResponseData add(@RequestBody AddCardCouponRequest requestParam) {
        try {
            return weixinCardCouponServiceContract.add(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "update", value = "更新微信卡券")
    @LogAnnotation
    @PostMapping("/update")
    public ResponseData update(@RequestBody UpdateCardCouponRequest requestParam) {
        try {
            return weixinCardCouponServiceContract.update(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "publish", value = "发布微信卡券")
    @LogAnnotation
    @PostMapping("/publish")
    public ResponseData publish(@RequestBody PublishCardRequest requestParam) {
        try {
            return weixinCardCouponServiceContract.publish(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "updateStatus", value = "更新微信卡券状态")
    @LogAnnotation
    @PostMapping("/updateStatus")
    public ResponseData updateStatus(@RequestParam Long id, @RequestParam Integer status) {
        try {
            WeixinCardCoupon weixinCardCoupon = weixinCardCouponServiceContract.selectById(id);
            if (weixinCardCoupon != null) {
                weixinCardCoupon.setStatus(status);
                weixinCardCoupon.setUpdateAt(new Date());
                weixinCardCouponServiceContract.updateAllColumnById(weixinCardCoupon);
                return ResponseUtil.success();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "updateStock", value = "更新微信卡券库存")
    @LogAnnotation
    @PostMapping("/updateStock")
    public ResponseData updateStock(@RequestParam Long id, @RequestParam Integer stockQty) {
        try {
            WeixinCardCoupon weixinCardCoupon = weixinCardCouponServiceContract.selectById(id);
            if (weixinCardCoupon != null) {
                weixinCardCoupon.setStockQty(stockQty);
                weixinCardCoupon.setUpdateAt(new Date());
                weixinCardCouponServiceContract.updateAllColumnById(weixinCardCoupon);
                return ResponseUtil.success();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        return ResponseUtil.fail();
    }

    @ApiOperation(nickname = "get", value = "得到微信卡券")
    @LogAnnotation
    @PostMapping("/get")
    public ResponseData get(@RequestBody GetCardCouponRequest requestParam) {
        try {
            return weixinCardCouponServiceContract.get(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "getList", value = "得到微信卡券列表")
    @LogAnnotation
    @PostMapping("/getList")
    public ResponseData getList(@RequestBody GetCardCouponListRequest requestParam) {
        try {
            return weixinCardCouponServiceContract.getList(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }

    @ApiOperation(nickname = "search", value = "搜索微信卡券")
    @LogAnnotation
    @PostMapping("/search")
    public ResponseData search(@RequestBody SearchCardCouponRequest requestParam) {
        try {
            return weixinCardCouponServiceContract.search(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }
}
