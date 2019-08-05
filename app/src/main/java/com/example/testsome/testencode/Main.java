package com.example.testsome.testencode;

import com.example.testsome.testencode.misc.BASE64Decoder;
import com.example.testsome.testencode.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by philer on 2019/7/25.
 * Describe:
 */
public class Main {
    public static void main(String[] args) {
        try {
            String encode = aesEncrypt("868717039272836", "passwordpassword");
            System.out.println("after encode : " + encode);
//            String decode = aesDecrypt(encode, "passwordpassword");
//            System.out.println("after decode : "+decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return new BASE64Encoder().encode(bytes);
    }

    public static String aesDecrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
}
