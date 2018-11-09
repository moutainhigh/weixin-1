package cn.siyue.platform.weixin.client.advice;

import cn.siyue.platform.base.ResponseData;
import cn.siyue.platform.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gavin
 * @description:
 * @date 2017/8/24 18:00
 */
@ControllerAdvice
public class WebControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(WebControllerAdvice.class);

    @ExceptionHandler
    @ResponseBody
    public ResponseData handleException(Throwable e) {
        logger.error(String.format("出现异常：%s", e.getMessage()));
        return ResponseUtil.fail();
    }
}
