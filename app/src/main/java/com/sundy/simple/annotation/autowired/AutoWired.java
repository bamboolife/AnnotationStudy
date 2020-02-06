package com.sundy.simple.annotation.autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-12 21:53
 * 描述：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoWired {
}
