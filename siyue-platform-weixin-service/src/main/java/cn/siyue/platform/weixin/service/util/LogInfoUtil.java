package cn.siyue.platform.weixin.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gavin
 * @description:
 * @date 2017/6/15 15:33
 */
public class LogInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogInfoUtil.class);

    public static String getRequestLogInfo(String reqUrl, Object reqParam, String respParam) {
        return String.format("请求URL：【%s】，%n请求参数：【%s】，%n响应参数：【%s】", reqUrl, reqParam, respParam);
    }

}
