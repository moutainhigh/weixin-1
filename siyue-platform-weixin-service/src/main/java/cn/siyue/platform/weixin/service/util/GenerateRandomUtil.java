/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.weixin.service.util;

import java.util.Random;

public class GenerateRandomUtil {

  /**
   * 生成length位随机数
   * length:位数
   */
  public static String generateCode(int length){
    String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
    Random rand = new Random();
    StringBuffer flag = new StringBuffer();
    for (int j = 0; j < length; j++){
      flag.append(sources.charAt(rand.nextInt(9)) + "");
    }
    return flag.toString();
  }
}
