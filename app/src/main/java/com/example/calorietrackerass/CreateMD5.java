package com.example.calorietrackerass;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author pibigstar
 *
 */
public class CreateMD5 {
    //salt
    //private static final String salt = "&%5123***&&%%$$#@";
    public static String encrypt(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}