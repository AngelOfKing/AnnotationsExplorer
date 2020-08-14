package com.bert.processor;

import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * @Author: bertking
 * @ProjectName: AnnotationsExplorer
 * @CreateAt: 2020/8/14 4:20 PM
 * @UpdateAt: 2020/8/14 4:20 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 仅仅是为了展示增量注解解释器的配置项而用，无实际意义(EventBus 同款)
 */
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.AGGREGATING) //增量聚合编译，
public class IncrementalProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
