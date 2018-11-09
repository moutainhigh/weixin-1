package cn.siyue.platform.weixin.service.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


/**
 * AES对称加密算法工具类
 */
public final class AESUtil {

    private AESUtil(){}
    
    public static void main(String arg[]) throws Exception{
//    	String requestToken = "v/TDTMBFo8nTAdGu/kAyKB6aBM/03gIBJOZeoAiXM65f4zq+7I0O+nJLMh3jnoNd";
//    	requestToken = URLDecoder.decode(requestToken);
//    	System.out.println(decrypt(requestToken));
    	System.out.println(System.currentTimeMillis()+"_"+"3313"+"_"+"20160106001"+"_"+"521376795");
    	String aesKey = generateKey();
    	System.out.println("aesKey: " + aesKey);
    	String key = encrypt(System.currentTimeMillis()+"_"+"3313"+"_"+"20160106001"+"_"+"521376795", aesKey);
    	System.out.println(key);
    	
    	System.out.println(decrypt(key, aesKey));
    }
    

    /**
     * 使用AES算法加密
     * @param content  待加密内容
     * @param key  加密密匙
     * @return  加密后的字符串
     */
    public static String encrypt(String content, String key) {
        try {
	            Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
	            aesECB.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	            byte[] result = aesECB.doFinal(content.getBytes());
	            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用默认的密匙加密
     * @param content 待加密的内容
     * @return
     */
    /*public static String encrypt(String content){
        return encrypt(content, SecurityConfig.AES_KEY);
    }*/


    /**解密
     * @param content  待解密内容
     * @param key 解密密钥
     * @return  解密后的字符串
     * @throws Exception 
     */
    public static String decrypt(String content, String key) throws Exception {
    	if(null != content){
    		try {
    			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
    			SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
    			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
    			byte[] result = Base64.decodeBase64(content);
    			return new String(cipher.doFinal(result)); // 解密
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw e;
    		}
    	}
        return null;
    }

    /**
     * 使用默认的密匙解密
     * @return
     * @throws Exception 
     */
    /*public static String decrypt(String content) throws Exception{
    	return decrypt(content, SecurityConfig.AES_KEY);
    }*/
    
    
    public static String generateKey(){
        Key key = null;
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			key = keygen.generateKey();
			byte[] keyBytes = key.getEncoded();
			return Base64.encodeBase64String(keyBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
       return null;
    }

}
