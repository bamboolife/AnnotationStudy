package com.sundy.simple.annotation.db;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-11 19:35
 * 描述：建表操作
 */
@TableDB(table = "student",dbName="demo.db",version=1)
public class Student extends AbstractDB {
    @ColumDB(column = "name")
    public String name;
    @ColumDB(column = "age")
    public String age;
    @ColumDB(column = "sex")
    public String sex;
}
