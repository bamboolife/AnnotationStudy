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
 * 创建时间：2020-01-13 12:03
 * 描述：
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Factory {
    /**
     * 工厂的名字
     */
    Class<?> type();

    /**
     * 用来表示生成哪个对象的唯一id
     */
    String id();
}
