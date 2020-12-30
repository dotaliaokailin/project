package com.liao.test;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroMD5Test {
    public static void main(String[] args) {
        //使用md5
        Hash md5Hash = new Md5Hash("123456");
        System.out.println(md5Hash.toHex());
        //使用md5 + 盐
        md5Hash = new Md5Hash("123456","salt");
        System.out.println(md5Hash.toHex());
        //使用md5 + 盐 + hash散列
        md5Hash = new Md5Hash("123456","salt", 1024);
        System.out.println(md5Hash.toHex());
    }
}
