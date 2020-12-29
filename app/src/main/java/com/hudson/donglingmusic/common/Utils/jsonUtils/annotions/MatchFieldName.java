package com.hudson.donglingmusic.common.Utils.jsonUtils.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**当使用此注解时，表示当前类的成员变量如果未使用@JsonKey注解，则直接使用成员的名称作为key
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchFieldName {
}
