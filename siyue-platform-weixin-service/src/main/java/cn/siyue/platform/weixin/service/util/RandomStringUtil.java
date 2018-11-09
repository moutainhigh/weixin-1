package cn.siyue.platform.weixin.service.util;

import java.util.Random;

public class RandomStringUtil {

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成17位的数字,生成规则：'type'+时间戳+6位数随机数
     * @param type
     * @return
     */
    public static String getCurrentAndRandom(String type) {
        String current = String.valueOf(System.currentTimeMillis());
        int max = 999999;
        int min = 100000;
        Random random = new Random();
        int randomNumber = random.nextInt(max) % (max - min + 1) + min;
        current = type.toUpperCase() + current + randomNumber;
        return current;
    }

    public static String getRandomNumber(int length) { //length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
