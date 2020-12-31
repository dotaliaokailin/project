package com.liao.util;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Random;

/**
 * 盐工具
 */
public class SaltUtil {

    public static String getSalt(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890~!@#$%^&*()".toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(chars[new Random().nextInt(chars.length)]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getSalt(4));
    }
}
