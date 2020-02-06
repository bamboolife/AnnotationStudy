package com.sundy.test_compiler;

import javax.lang.model.element.Element;

/**
 * 项目名称：AnnotationStudy
 *
 * @Author bamboolife
 * 邮箱：core_it@163.com
 * 创建时间：2020-01-13 14:04
 * 描述：
 */
public class ProcessingException extends Exception{
    Element element;
    public ProcessingException(Element element,String msg,Object... args){
        super(String.format(msg,args));
        this.element=element;
    }

    public Element getElement(){
        return element;
    }
}
