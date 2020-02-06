package com.sundy.test_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-13 18:27
 * 描述：
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Optional {
    String stringZValue() default "";
    int intValue() default 0;
    float floatValue() default 0f;
    boolean booleanValue() default false;
}
