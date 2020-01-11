# Annotation学习和研究

## 现在好多第三方框架都在使用注解的方式来实现。既然都在使用肯定有一定的优势，所以就来研究下，注解的好处和优势。为什么大家都在使用
## 此工程只用来学习、研究和笔记的记录和网上资源的整理

### 优势
- 我们平常在使用Java进行开发Android时，经常会需要写很多重复冗余的样板代码，开发中最常见的一种，就是findViewById了，如果一个界面有很多View，写起来那叫一个要死要死。于是我们注解处理器可以帮助解决冗余的代码的，
- 由于是在编译器进行生成的代码，并不是通过反射实现，所以性能优势是非常高的
- 加快开发速度，由于减少了写繁琐的代码，会对项目进度起有利的作用

### 注解原理

在android开发中，比较常用到的第三方库中，有不少用到了 注解处理器(Annotation Processor)。 比较常见的就有 Butterknife，Dagger2，DBFlow 等。

### 注解

Java中存在不少关于注解的Api, 比如@Override用于覆盖父类方法，@Deprecated表示已舍弃的类或方法属性等，android中又多了一些注解的扩展，如@NonNull, @StringRes, @IntRes等。

### 代码自动生成

使用代码自动生成，一是为了提高编码的效率，二是避免在运行期大量使用反射，通过在编译期利用反射生成辅助类和方法以供运行时使用。

### 什么是注解

注解是一种元数据, 可以添加到java代码中. 类、方法、变量、参数、包都可以被注解，注解对注解的代码没有直接影响.

### 元注解

元注解就是用来定义注解的注解.其作用就是定义注解的作用范围, 使用在什么元素上等等
<br>元注解共有四种@Retention, @Target, @Inherited, @Documented
#### @Retention

@Retention定义了该Annotation被保留的时间长短：某些Annotation仅出现在源代码中，而被编译器丢弃；而另一些却被编译在class文件中；编译在class文件中的Annotation可能会被虚拟机忽略，而另一些在class被装载时将被读取（请注意并不影响class的执行，因为Annotation与class在使用上是被分离的）。使用这个meta-Annotation可以对 Annotation的“生命周期”限制。

作用：表示需要在什么级别保存该注释信息，用于描述注解的生命周期（即：被描述的注解在什么范围内有效）取值（RetentionPoicy）有：
- SOURCE:在源文件中有效（即源文件保留）
- CLASS:在class文件中有效（即class保留）
- RUNTIME:在运行时有效（即运行时保留）

#### @Target
@Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。
<br>作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）取值(ElementType)有：
- CONSTRUCTOR:用于描述构造器
- FIELD:用于描述域
- LOCAL_VARIABLE:用于描述局部变量
- METHOD:用于描述方法
- PACKAGE:用于描述包
- PARAMETER:用于描述参数
- TYPE:用于描述类、接口(包括注解类型) 或enum声明



#### @Inherited

#### @Documented


#### 注解处理器的处理步骤主要有以下：
1. 在java编译器中构建
2. 编译器开始执行未执行过的注解处理器
3. 循环处理注解元素(Element)，找到被该注解所修饰的类，方法，或者属性
4. 生成对应的类，并写入文件
5. 判断是否所有的注解处理器都已执行完毕，如果没有，继续下一个注解处理器的执行(回到步骤1)

![image](https://github.com/bamboolife/AnnotationStudy/blob/master/image/annotation_flow.png)

#### 自定义注解处理器

创建工程

- 首先创建一个project。
- 创建test_annotations， 这是一个纯java的module，不包含任何android代码，只用于存放注解。
- 创建test_compiler， 这同样是一个纯java的module。该module依赖于步骤2创建的module_annotation,处理注解的代码都在这里，该moduule最终不会被打包进apk，所以你可以在这里导入任何你想要的任意大小依赖库。
- 创建test_api, 对该module不做要求，可以是android library或者java library或者其他的。该module用于调用步骤3生成的辅助类方法。

#### 1.添加注解
- @interface 自定义注解，使用 @interface 作为类名修饰符
- @Target 该注解所能修饰的元素类型，可选类型如下：
```java
public enum ElementType {
    TYPE, //类
    FIELD, //属性
    METHOD, //方法
    PARAMETER, //参数
    CONSTRUCTOR, //构造函数
    LOCAL_VARIABLE, 
    ANNOTATION_TYPE,
    PACKAGE,
    TYPE_PARAMETER,
    TYPE_USE;

    private ElementType() {
    }
}
```
- @Retention 该注解的保留策略，有三种选项：
```java
public enum RetentionPolicy {
    SOURCE, //被编译器所忽略

    CLASS, //被编译器保留至类文件，但不会保留至运行时

    RUNTIME //保留至类文件，且保留至运行时，能在运行时反射该注解修饰的对象
}
```
#### 2.注解处理器

真正处理注解并生成代码的操作都在这里。 在写代码之前我们需要先导入两个重要的库，以及我们的注解模块：

```
compile 'com.google.auto.service:auto-service:1.0-rc4'
compile 'com.squareup:javapoet:1.9.0'
implementation project(':test_annotations')
```
