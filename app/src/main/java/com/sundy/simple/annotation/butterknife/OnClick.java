package com.sundy.simple.annotation.butterknife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-12 23:03
 * 描述：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    int value();
}
