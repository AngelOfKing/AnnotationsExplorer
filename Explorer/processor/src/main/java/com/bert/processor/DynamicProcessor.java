package com.bert.processor;

import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * @Author: bertking
 * @ProjectName: AnnotationsExplorer
 * @CreateAt: 2020/8/14 4:33 PM
 * @UpdateAt: 2020/8/14 4:33 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: ButterKnife同款
 */
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.DYNAMIC) //增量动态编译，独立or聚合，是由getSupportedOptions()来控制的
public class DynamicProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }


    /**
     * 如果不覆写此方法，那么将会得到警告。
     * @return
     */
    @Override
    public Set<String> getSupportedOptions() {
        // return Collections.singleton("org.gradle.annotation.processing.isolating"); 等价
        return Collections.singleton(IncrementalAnnotationProcessorType.ISOLATING.getProcessorOption());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
