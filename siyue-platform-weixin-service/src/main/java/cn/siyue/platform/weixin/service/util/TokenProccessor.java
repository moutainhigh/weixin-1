/*
 * (C) Copyright 2018 Siyue Holding Group.
 */
package cn.siyue.platform.weixin.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import sun.misc.BASE64Encoder;

/**
 * 生成Token的工具类
 */
public class TokenProccessor {

  private TokenProccessor() {}

  private static final TokenProccessor instance = new TokenProccessor();

  public static TokenProccessor getInstance() {
    return instance;
  }

  /**
   * 根据帐号生成随机的生成Token
   */
  public String makeToken(String code) throws NoSuchAlgorithmException {
    String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + code;
    MessageDigest md = MessageDigest.getInstance("md5");
    byte md5[] = md.digest(token.getBytes());
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(md5);

  }
}
