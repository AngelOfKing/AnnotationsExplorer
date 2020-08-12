package com.bert.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: bertking
 * @ProjectName: AnnotationsExplorer
 * @CreateAt: 2020/8/10 5:09 PM
 * @UpdateAt: 2020/8/10 5:09 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 自定义注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface CusAnnotation {
    String name() default "";
    int value();
}