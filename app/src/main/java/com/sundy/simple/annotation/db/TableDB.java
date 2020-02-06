package com.sundy.simple.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-11 19:24
 * 描述：TableDB 注解用于表和数据库的配置参数。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableDB {
    String table();//表名
    String dbName() default "demoDb.db";//数据库名称
    int version() default 1;//版本号
}
