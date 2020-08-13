<!--
 * @Author: BertKing
 * @version: 
 * @Date: 2020-08-12 11:10:16
 * @LastEditors: BertKing
 * @LastEditTime: 2020-08-13 19:54:34
 * @FilePath: /undefined/Users/bertking/AnnotationsExplorer/README.md
 * @Description: 
-->
# AnnotationsExplorer
Do you really know Annotation？

关于此项目运行的任何问题，您都可以先到 [Issues](https://github.com/Bert-King/AnnotationsExplorer/issues) 里尝试获取有效信息.

---


# 1. 注解(Annotation)

Java 在 **JDK 1.5** 之后引入了 **注解**，我们先来简要谈一下注解的种类。

## 1.1 注解的分类
不同的划分方式将会导致不同的种类(如果面试过程中遇到此类问题，一定要展开去具体论述) PS:此问题仁者见仁，言之有理即可。 

1. 按照来源划分：

    a. 元注解( Target,  Retention,  Documented, Inherited,  Repeatable(1.8))

    b. 自定义注解(JDK内置的注解，第三方库的注解，我们自己定义的注解)


2. 按照注解的时机(作用域)划分：

    i. Source Code 注解

    ii. 编译时注解

    iii.运行时注解


具体的使用方法，网上信息很全，这里不展开论述。


---

# 2. 注解解释器(Annotation Processor)

顾名思义，**注解解释器**就是用来处理**注解**的 (废话一句)

如果对这方面有了解的话，那鼎鼎大名的**AbstractProcessor**一定是见识过的。我们一般在处理注解时，都需要来继承它。

但是到了这里，我们有了注解解释器，怎么让他运行呢？这里我们需要借助于Google出的 [AutoService](https://github.com/google/auto/tree/master/service),真心帮助我们省掉很多繁琐的步骤。使用方法更是超级简单：
只需要在我们自定义的注解上面再加上一个注解即可。
> @AutoService(Processor.class)

那它到底是怎么帮我们的呢？请参考这里:[传送门](https://github.com/Bert-King/AnnotationsExplorer/issues/6)

既然我们知道了AutoService的原理，就不难推测我们也可以自己创建**main/META-INF/services/javax.annotation.processing.Processor**,然后在该文件中写入：
> 自定义注解的绝对路径


# 3. AbstractProcessor的工作原理

>  An annotation that is present in the binary form may or may not be available at run time via the reflection libraries of the Java SE Platform. （二进制文件中的注解是通过Java SE平台的**反射库**来决定是否起作用的）

参考自: [Java Language Specification](https://docs.oracle.com/javase/specs/jls/se14/html/jls-9.html#jls-9.6.4.1)

另外，在**RetentionPolicy**的源码中也能得到**反射**的线索。
```Java
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     * 注解被编译器丢弃，只保留在源码中
     */
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     * 注解将会被编译器记录在Class文件中，但是运行时不必(这里用的是need)被虚拟机保留。默认行为
     */
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     * 注解将会被编译器记录在Class文件中，同时在运行时被虚拟机保留。所以通过反射读取。
     *
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}

```
> PS: 不知道你们纠结过CLASS和RUNTIME的区别没？至少有没有怀疑过CLASS不能被VM通过发射读取呢？稍后会有释惑...
----

通过上面的信息，我们明确地知道处理注解的武器 -———— **反射**。

下面我们可以来看一下**AbstractProcessor**的部分源码：

第一个重量级方法**process()** 是个抽象方法，它的逻辑是让我们开发者来续写篇章的。但是你也许会有疑问：
**参数annotations**从何而来？

```Java
    /**
     * {@inheritDoc}
     */
    public abstract boolean process(Set<? extends TypeElement> annotations,
                                    RoundEnvironment roundEnv);
```

第二个重量级方法**getSupportedAnnotationTypes()** 它来了，它来了...
```Java
 public Set<String> getSupportedAnnotationTypes() {
            // 从这里我们可以看出，根据反射来处理SupportedAnnotationTypes注解
            SupportedAnnotationTypes sat = this.getClass().getAnnotation(SupportedAnnotationTypes.class);
            boolean initialized = isInitialized();
            if  (sat == null) {
                if (initialized)
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                                                             "No SupportedAnnotationTypes annotation " +
                                                             "found on " + this.getClass().getName() +
                                                             ", returning an empty set.");
                return Set.of();
            } else {
                // 这里有个版本1.8的限制，这是因为在JDK1.9才开始支持module概念
                boolean stripModulePrefixes =
                        initialized &&
                        processingEnv.getSourceVersion().compareTo(SourceVersion.RELEASE_8) <= 0;
                // 这里sat.value其实就是SupportedAnnotationTypes的注解类型值，往下看
                return arrayToSet(sat.value(), stripModulePrefixes,
                                  "annotation type", "@SupportedAnnotationTypes");
            }
        }


@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface SupportedAnnotationTypes {
    /**
     * Returns the names of the supported annotation types.
     * @return the names of the supported annotation types
     */
    String [] value(); // 上面的sat.value是数组类型。
}

```

> PS :看到**SupportedAnnotationTypes**不禁让我想到**JDK 1.8** 新推出的[元注解(@Repeatable](https://docs.oracle.com/javase/specs/jls/se14/html/jls-9.html#jls-9.6.3) 有点鸡肋。

# 4. RetentionPolicy: RUNTIME VS RUNTIME
我们都知道**RetentionPolicy**有个值:
1. SOURCE
2. CLASS
3. RUNTIME

最容易引发人们争议的是**CLASS** 和**RUNTIME** 莫属。因为你可以将本项目的[CusAnnotation](https://github.com/Bert-King/AnnotationsExplorer/blob/master/Explorer/annotations/src/main/java/com/bert/annotations/CusAnnotation.java) 的 RetentionPolicy 分别设置为两者，进行比较。若无意外，都是可以正常的生成类。WTF？

> PS:在[AnnotationAtRuntime](https://github.com/Bert-King/AnnotationsExplorer/blob/master/Explorer/app/src/main/java/com/bert/annotationsexplorer/AnnotationAtRuntime.java)单独测试时会发现，只有RUNTIME才有效果。

---
是时候展现真的的技术啦。
> javap -v(erbose) XXX.java 去查看一下编译后的字节码吧。我确信你会有些许收获的。

有兴趣的朋友，我还是建议亲自试一下，这里直接给出结论

|RetentionPolicy|字节码的内容|
|---|---|
|CLASS|RuntimeVisibleAnnotations|
|RUNTIME|RuntimeVisibleAnnotations|

 其实关于两者的讨论早已不是什么密码。
 
 这里给出我所看过的一些讨论：
 1. [How do different retention policies affect my annotations?
](https://stackoverflow.com/questions/3107970/how-do-different-retention-policies-affect-my-annotations?noredirect=1&lq=1)

2. [RetentionPolicy CLASS vs. RUNTIME](https://stackoverflow.com/questions/5971234/retentionpolicy-class-vs-runtime/5971247#5971247)

3. [Java Annotations - looking for an example of RetentionPolicy.CLASS](https://stackoverflow.com/questions/3849593/java-annotations-looking-for-an-example-of-retentionpolicy-class?noredirect=1&lq=1)


这里给一些看完讨论后的心得体会：

> 在实际的开发过程中,只有在特殊情况下需要读取字节码而不是类加载器时才会考虑使用CLASS，其他情况下直接使用RUNTIME即可。
Ironically(具有讽刺意味的是),CLASS才是默认行为。JDK内置注解是不存在RetentionPolicy为CLASS的(Spring貌似挺多的)。

当然啦，对于Android开发者来讲，如果足够细心的话，倒是会发现几个特殊情况(TargetApi,SuppressLint)：

android.annotation包下：

```Java
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
public @interface SuppressLint {
    /**
     * The set of warnings (identified by the lint issue id) that should be
     * ignored by lint. It is not an error to specify an unrecognized name.
     */
    String[] value();
}


@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
public @interface SuppressLint {
    /**
     * The set of warnings (identified by the lint issue id) that should be
     * ignored by lint. It is not an error to specify an unrecognized name.
     */
    String[] value();
}
```

# 5.高阶知识点

[注解的增量更新]()

---
乘兴而来乎?...兴尽而归乎？...