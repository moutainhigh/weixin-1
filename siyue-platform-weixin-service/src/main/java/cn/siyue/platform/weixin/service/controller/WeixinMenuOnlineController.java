package cn.siyue.platform.weixin.service.controller;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.httplog.annotation.LogAnnotation;
import cn.siyue.platform.util.ResponseUtil;
import cn.siyue.platform.weixin.common.common.PageRequest;
import cn.siyue.platform.weixin.service.service.WeixinMenuOnlineServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixinMenuOnline")
public class WeixinMenuOnlineController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinMenuOnlineController.class);

    @Autowired
    private WeixinMenuOnlineServiceContract weixinMenuOnlineServiceContract;

    @LogAnnotation
    @PostMapping("/getList")
    public ResponseData getList(@RequestBody PageRequest requestParam) {
        try {
            return weixinMenuOnlineServiceContract.getList(requestParam);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseUtil.fail();
        }
    }
}
