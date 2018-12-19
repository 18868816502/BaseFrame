package com.sly.baseframe.net;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil {
    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = "wechatPay--mchid";

    private static String IV = "wechatPay--appid";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @return
     */
    public static String encrypt(String data) {
        try {
            byte[] raw = KEY.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = null;//"算法/模式/补码方式"
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encode(encrypted);//此处使用BAES64做转码功能，同时能起到2次
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static String encrypt(String data) {//正式环境还未发布  等上了之后切换为上个方法
//        return data;
//    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @return
     */
    public static String desEncrypt(String data) throws Exception {
        byte[] raw = KEY.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.decode(data);//先用bAES64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }


//    public static void main(String[] args) throws Exception {
//        Integer content = 123456;
//        System.out.println("加密前：" + content);
//        System.out.println("加密密钥和解密密钥：" + KEY);
//        String encrypt = encrypt(content.toString());
//        System.out.println("加密后：" + encrypt);
//        String decrypt = desEncrypt(encrypt);
//        System.out.println("解密后：" + decrypt);
//    }


}
