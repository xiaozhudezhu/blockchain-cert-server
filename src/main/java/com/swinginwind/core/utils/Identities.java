/**
 * Copyright (c) 2005-2012 gener-tech.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.swinginwind.core.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author calvin
 */
public class Identities {

    private static SecureRandom random = new SecureRandom();

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid2() {
        return UUID.randomUUID().toString();
    }
}
