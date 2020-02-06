package com.sundy.simple.annotation.autowired;

import com.sundy.simple.annotation.autowired.AutoWired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-12 21:54
 * 描述：
 */
public class AutoWiredUtils {
    public static void bind(Object object) {
        Class parentClass = object.getClass();
        Field[] fields = parentClass.getDeclaredFields();
        for (final Field field : fields) {
            AutoWired autoWiredAnnotation = field.getAnnotation(AutoWired.class);
            if (autoWiredAnnotation != null) {
                field.setAccessible(true);
                try {
                    Class<?> autoCreateClass = field.getType();
                    Constructor autoCreateConstructor = autoCreateClass.getConstructor();
                    field.set(object, autoCreateConstructor.newInstance());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
