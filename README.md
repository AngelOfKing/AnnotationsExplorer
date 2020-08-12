<!--
 * @Author: BertKing
 * @version: 
 * @Date: 2020-08-12 11:10:16
 * @LastEditors: BertKing
 * @LastEditTime: 2020-08-12 21:15:18
 * @FilePath: /undefined/Users/bertking/AnnotationsExplorer/README.md
 * @Description: 
-->
# AnnotationsExplorer
Do you really know Annotation？

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



