package cn.siyue.platform.weixin.service.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

    public static String encodeBase64(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    public static byte[] decodeBase64(String base64String) {
        return Base64.decodeBase64(base64String);
    }
}
