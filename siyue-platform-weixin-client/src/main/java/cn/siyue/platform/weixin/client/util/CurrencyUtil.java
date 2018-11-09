package cn.siyue.platform.weixin.client.util;

import java.math.BigDecimal;

public class CurrencyUtil {

    public static BigDecimal setHalfUp(BigDecimal bd) {
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 元转分
     * @param bd
     * @return
     */
    public static BigDecimal yuanToFen(BigDecimal bd) {
        return bd.multiply(new BigDecimal(100));
    }

    /**
     * 分转元
     * @param bd
     * @return
     */
    public static BigDecimal fenToYuan(BigDecimal bd) {
        return bd.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
    }

}
