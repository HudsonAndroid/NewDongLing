package com.hudson.donglingmusic.common.Utils.jsonUtils.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**表示当前成员变量在json字符串里的key
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonKey {
    String value();
}
