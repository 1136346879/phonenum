package com.news.extend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/20 11:23
 * 电子邮箱：
 * 描述:
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface DebugLog {

}
