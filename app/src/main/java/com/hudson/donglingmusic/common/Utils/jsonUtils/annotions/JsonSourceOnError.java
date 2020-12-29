package com.hudson.donglingmusic.common.Utils.jsonUtils.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSourceOnError {
    //当解析出错的时候，会把原始字符串写到这个注解标识的成员变量里
    String value();
}
