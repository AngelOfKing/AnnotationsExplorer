package com.bert.annotationsexplorer;

import com.bert.annotations.CusAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author: bertking
 * @ProjectName: AnnotationsExplorer
 * @CreateAt: 2020/8/13 10:45 AM
 * @UpdateAt: 2020/8/13 10:45 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 了解运行时如何访问注解(反射)
 *
 */

@Deprecated()
@CusAnnotation(name = "Just for test",value = 2020)
public class AnnotationAtRuntime {

    @Deprecated
    private int date ;

    public void showDate(){

    }

    public static void main(String[] args) {

        Class<AnnotationAtRuntime> clazz = AnnotationAtRuntime.class;

        /* isAnnotationPresent()方法适用于Package, Class，Method，Field*/
        boolean cusAnnoPresent = clazz.isAnnotationPresent(CusAnnotation.class);
        System.out.println("自定义注解(CusAnnotation)是否存在:"+cusAnnoPresent); // true

        boolean deprecatedAnnoPresent = clazz.isAnnotationPresent(Deprecated.class);
        System.out.println("内置注解(deprecatedAnnoPresent)是否存在:"+deprecatedAnnoPresent); // true

        boolean suppressWarningsAnnoPresent = clazz.isAnnotationPresent(SuppressWarnings.class);
        System.out.println("内置注解(suppressWarningsAnnoPresent)是否存在:"+suppressWarningsAnnoPresent);// false

        try {
            Method showDateMethod = clazz.getMethod("showDate");
            boolean overrideAnnoPresentInMethod = showDateMethod.isAnnotationPresent(Override.class);
            System.out.println("showDate()是否存在Override注解:"+overrideAnnoPresentInMethod); // false

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 打印annotations
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation :
                annotations) {
            System.out.println(annotation);

        }

    }
}
