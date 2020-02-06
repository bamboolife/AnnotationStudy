package com.sundy.simple.annotation.butterknife;

import android.app.Activity;
import android.view.View;

import com.sundy.simple.annotation.butterknife.BindView;
import com.sundy.simple.annotation.butterknife.ContentView;
import com.sundy.simple.annotation.butterknife.OnClick;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-12 05:46
 * 描述：
 */
public class ButterknifeProcess {

    public static void bind(Activity object) {

        Class<?> objClass = object.getClass();
        ContentView contentView = objClass.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.id();
            try {
                Method method = objClass.getMethod("setContentView", int.class);
                method.invoke(object, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        View rootView = object.getWindow().getDecorView();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                int viewId = bindView.value();
                if (viewId != -1) {
                    try {
                        field.setAccessible(true);
                        field.set(object, rootView.findViewById(viewId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        Method methods[] = objClass.getMethods();
        for (Method method : methods) {
            //找到添加了OnClick注解的方法
            OnClick clickMethod = method.getAnnotation(OnClick.class);
            if (clickMethod != null) {
                int id = clickMethod.value();
                final View view = object.findViewById(id);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(object, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }
}
