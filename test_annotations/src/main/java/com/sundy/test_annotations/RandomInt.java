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
 * 创建时间：2020-01-11 14:40
 * 描述：
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = ElementType.FIELD)
public @interface RandomInt {
    int minValue() default 0;
    int maxValue() default 65535;
}
