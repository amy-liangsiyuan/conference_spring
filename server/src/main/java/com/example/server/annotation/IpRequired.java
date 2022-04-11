package com.example.server.annotation;
import java.lang.annotation.*;

/**
 * @author Amy
 * @date 2021-12-15 15:13
 * @description
 * @package cn.lsy.server.annotation
 * @title
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IpRequired {
}
