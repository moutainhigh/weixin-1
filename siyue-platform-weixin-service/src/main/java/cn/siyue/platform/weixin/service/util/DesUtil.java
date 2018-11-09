/**
 * Copyright (c) 2016 SIH, Inc. All rights reserved.
 * This software is the confidential and proprietary information of 
 * SIH, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the 
 * license agreement you entered into with SIH.
 */
package cn.siyue.platform.weixin.service.util;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * DES加密解密工具 - 主长益
 * @ClassName: DESUtil.java
 * @author zhangxy
 * @date 2016年9月6日 下午12:27:55
 *
 */
public class DesUtil {
	
	public static final String IBDS_DES = "DES/ECB/NoPadding";


    public static void main(String[] args) throws Exception {
    	String DES_KEY_STRING = "04834312";
//		String oriStr = "Loser";
		String oriStr = "111111112fe0a221-f7e3-47be-ac73-90d71023edb7";
		String desStr = encrypt(oriStr, DES_KEY_STRING);
		System.out.println("desStr:" + desStr);
		String dedesStr = decrypt(desStr, DES_KEY_STRING);
		System.out.println("dedesStr:" + dedesStr);
    	
//    	String desStr1 = "KRA+y0rushQ=";
//    	System.out.println(decrypt(desStr1, DES_KEY_STRING));
	}
    
    
    /**
     * 加密
     * @Title: encrypt
     * @param message 明文
     * @param key 密钥
     * @return
     * @throws Exception
     * @return String
     * @author zhangxy
     * @throws
     */
    public static String encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(IBDS_DES);
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        SecureRandom iv = new SecureRandom();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return encodeBase64(cipher.doFinal(zerosPadding(message.getBytes())));
    }
 
    /**
     * 解密
     * @Title: decrypt
     * @param message 密文
     * @param key 密钥
     * @return
     * @throws Exception
     * @return String
     * @author zhangxy
     * @throws
     */
    public static String decrypt(String message, String key) throws Exception {
 
        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
        Cipher cipher = Cipher.getInstance(IBDS_DES);
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        SecureRandom iv = new SecureRandom();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(deZeroPadding(retByte));
    }
 
    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
 
        return digest;
    }
 
    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
 
        return hexString.toString();
    }
 

    public static String encodeBase64(byte[] b) {
    	return Base64.encodeBase64String(b);
    }
     
    public static byte[] decodeBase64(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    
    
    

    private static byte[] zerosPadding(byte[] bytes) {
		byte[] result = new byte[((bytes.length+7)/ 8) * 8];
		System.arraycopy(bytes, 0, result, 0, bytes.length);
		return result;
	}
    
    private static byte[] deZeroPadding(byte[] bytes) {
    	byte[] result = null;
    	byte[] right = Arrays.copyOfRange(bytes, (bytes.length - 7), bytes.length);
    	if (right[0] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 7));
		} else if (right[1] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 6));
		} else if (right[2] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 5));
		} else if (right[3] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 4));
		} else if (right[4] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 3));
		} else if (right[5] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 2));
		} else if (right[6] == 0) {
    		result = Arrays.copyOfRange(bytes, 0, (bytes.length - 1));
		} else {
    		result = bytes;
		}
    	return result;
    }
    
}
