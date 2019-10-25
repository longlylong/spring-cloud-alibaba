package com.thatday.common.utils;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;


public class AESCrypt {

    private static final String key = "afaf1^*^%82ahkff8&^oif29@8jHFIf*!FUO5";

    private static Cipher getCipher(int decryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        //参考java文档https://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(decryptMode, key, getIV());
        return cipher;
    }

    private static AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(String cryptedText) {
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] bytes = Base64.getDecoder().decode(cryptedText);//Base64.decode(cryptedText, Base64.DEFAULT);
            byte[] decrypted = cipher.doFinal(bytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}