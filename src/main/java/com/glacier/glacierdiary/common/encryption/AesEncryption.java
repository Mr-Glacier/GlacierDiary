package com.glacier.glacierdiary.common.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES 加密解密方法
 *
 * @author Mr-Glaicer
 */
public class AesEncryption {

    /*
       SecretKey secretKey = decodeKeyFromString(secretKeyString);
       String encryptedClientId = encrypt(clientId, secretKey);
       String decryptedClientId = decrypt(encryptedClientId, secretKey);
     */

    /**
     * 生成AES 密钥方法
     */
    public String createAesKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        // 选择 128、192 或 256 位
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Key: " + encodedKey);
        return encodedKey;
    }

    /**
     * 将字符串解码为 SecretKey 对象
     */
    public  SecretKey decodeKeyFromString(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    /**
     * AES 加密方法
     */
    public  String encrypt(String strToEncrypt, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES 解密方法
     */
    public  String decrypt(String strToDecrypt, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        // 生成密钥
        AesEncryption aesEncryption = new AesEncryption();
        String newKey = aesEncryption.createAesKey();
        System.out.println(newKey);
        // 加密测试
        // 根据密钥生成 SecretKey 对象
        SecretKey secretKey = aesEncryption.decodeKeyFromString(newKey);
        // 加密
        String encryptedClientId = aesEncryption.encrypt("pass:2025123", secretKey);
        System.out.println("加密后的数据:"+encryptedClientId);
        // 解密
        String decryptedClientId = aesEncryption.decrypt(encryptedClientId, secretKey);
        System.out.println("解密后的数据:"+decryptedClientId);
    }

}
