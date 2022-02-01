package com.xh.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * UnityEncryptUtil -- 加解密码工具类
 *
 * @author xh
 * @date 2022/1/25
 */
public class XhEncryptUtil {

    /**
     * 编码
     */
    private final static String ENCODE = "UTF-8";

    /**
     * MD5的KEY
     */
    public static final String KEY_MD5 = "MD5";

    /**
     * DES加密,DES对称属于对称加密
     *
     * @param str    待加密码字符串
     * @param desKey DES密钥,长度必须8的倍数
     * @return 返回密文
     * @throws Exception 异常
     */
    public static String encryptDes(String str, String desKey) throws Exception {
        byte[] b = encryptDes(str.getBytes(), desKey);
        return byte2hex(b);
    }

    /**
     * DES加密,DES对称属于对称加密
     *
     * @param bytes  待加密的二进制
     * @param desKey DES密钥,长度必须8的倍数
     * @return 密文二进制
     * @throws Exception 异常
     */
    public static byte[] encryptDes(byte[] bytes, String desKey) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(desKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        return cipher.doFinal(bytes);
    }

    /**
     * DES解密,DES对称属于对称加密
     *
     * @param str    待解密字符串
     * @param desKey DES密钥
     * @return 解密后的字符串
     * @throws Exception 异常
     */
    public static String decryptDes(String str, String desKey) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(desKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        return new String(decryptDes(hex2byte(str.getBytes()), desKey));
    }

    /**
     * DES解密
     *
     * @param bytes  待解密二进制
     * @param desKey DES密钥
     * @return 解密后的二进制
     * @throws Exception 异常
     */
    public static byte[] decryptDes(byte[] bytes, String desKey) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(desKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        return cipher.doFinal(bytes);
    }

    /**
     * BASE64编码
     *
     * @param str 待编码的字符串
     * @return BASE64编码后的字符串
     * @throws Exception 异常
     */
    public static String encryptBase64(String str) throws Exception {
        return encryptBase64Byte(str.getBytes());
    }

    /**
     * base64编码
     *
     * @param bytes 二进制
     * @return BASE64编码后的字符串
     */
    public static String encryptBase64Byte(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    /**
     * BASE64解码
     *
     * @param str 待解码的字符串
     * @return 解码后的字符串
     */
    public static String decryptBase64(String str) {
        return new String(decryptBase64Byte(str));
    }

    /**
     * base64解码
     *
     * @param str 待解码的字符串
     * @return 解码后的二进制
     */
    public static byte[] decryptBase64Byte(String str) {
        str = str.replaceAll("\r|\n", "");
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(str);
    }

    /**
     * 二进制转十六进制字符
     *
     * @param b       二进制
     * @param toUpper 返回值是否转大写
     * @return 十六进制字符串
     */
    public static String byte2hex(byte[] b, boolean toUpper) {
        StringBuilder hs = new StringBuilder();
        String temp;
        for (byte value : b) {
            temp = (Integer.toHexString(value & 0XFF));
            if (temp.length() == 1) {
                hs.append("0").append(temp);
            } else {
                hs.append(temp);
            }
        }
        if (toUpper) {
            hs = new StringBuilder(hs.toString().toUpperCase());
        }
        return hs.toString();
    }

    /**
     * 二进制转十六进制字符,返回大写
     *
     * @param b 二进制
     * @return 十六进制字符串
     */
    public static String byte2hex(byte[] b) {
        return byte2hex(b, true);
    }

    /**
     * 十六进制转二进制
     *
     * @param b 十六进制byte数组
     * @return 二进制byte数组
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("转换失败，长度不是偶数");
        } else {
            byte[] b2 = new byte[b.length / 2];
            for (int i = 0; i < b.length; i += 2) {
                String item = new String(b, i, 2);
                b2[i / 2] = (byte) Integer.parseInt(item, 16);
            }
            return b2;
        }
    }

    /**
     * MD5摘要
     *
     * @param str     字符串
     * @param toUpper 返回值是否转大写
     * @return MD5值
     * @throws Exception 异常
     */
    public static String encryptMd5(String str, boolean toUpper)
            throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        byte[] distByte = md5.digest(str.getBytes(ENCODE));
        return byte2hex(distByte, toUpper);
    }

    /**
     * MD5摘要
     *
     * @param str 字符串
     * @return 返回大写的MD5值
     * @throws Exception 异常
     */
    public static String encryptMd5(String str) throws Exception {
        return encryptMd5(str, true);
    }
}
