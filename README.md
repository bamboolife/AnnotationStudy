# Annotation学习和研究

## 现在好多第三方框架都在使用注解的方式来实现。既然都在使用肯定有一定的优势，所以就来研究下，注解的好处和优势。为什么大家都在使用
## 此工程只用来学习、研究和笔记的记录和网上资源的整理

### 为什么要使用注解
### 优点
- 我们平常在使用Java进行开发Android时，经常会需要写很多重复冗余的样板代码，开发中最常见的一种，就是findViewById了，如果一个界面有很多View，写起来那叫一个要死要死。于是我们注解处理器可以帮助解决冗余的代码的，
- 由于是在编译器进行生成的代码，并不是通过反射实现，所以性能优势是非常高的
- 加快开发速度，由于减少了写繁琐的代码，会对项目进度起有利的作用
- 节省配置，减少配置文件
- 编译时即可查看正确与否，提高效率
- 相对于配置文件而言更为简洁，易于配置。
### 缺点
- 增加了程序的耦合性，因为注解保存在class文件中，而且比较分散
- 若要对配置文件修改需要重新编译

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
| RetentionPoicy类型 | 说明 |
|:------- |:----------- |
| SOURCE |注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃）|
| CLASS  | 注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期 |
| RUNTIME | 注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在 |

>SOURCE < CLASS < RUNTIME,前者能作用的地方后者一定也能作用.

#### @Target
@Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。
<br>作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）取值(ElementType)有：
| ElementType类型 | 说明 |
|:------- |:----------- |
| CONSTRUCTOR | 用于描述构造器 |
| FIELD | 用于描述域 |
| LOCAL_VARIABLE | 用于描述局部变量 |
| METHOD | 用于描述方法 |
| PACKAGE | 用于描述包 |
| PARAMETER | 用于描述参数 |
| TYPE  | 用于描述类、接口(包括注解类型) 或enum声明 |
| ANNOTATION_TYPE | 注解 |

#### @Inherited
@Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。

注意：@Inherited annotation类型是被标注过的class的子类所继承。类并不从它所实现的接口继承annotation，方法并不从它所重载的方法继承annotation。
　　
当@Inherited annotation类型标注的annotation的Retention是RetentionPolicy.RUNTIME，则反射API增强了这种继承性。如果我们使用java.lang.reflect去查询一个@Inherited annotation类型的annotation时，反射代码检查将展开工作：检查class和其父类，直到发现指定的annotation类型被发现，或者到达类继承结构的顶层。

其中, @Retention是定义保留策略, 直接决定了我们用何种方式解析. SOUCE级别的注解是用来标记的, 比如Override, SuppressWarnings. 我们真正使用的类型是CLASS(编译时)和RUNTIME(运行时)


#### @Documented
@Documented用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。Documented是一个标记注解，没有成员。

### 自定义注解
使用@interface自定义注解时，自动继承了java.lang.annotation.Annotation接口，由编译程序自动完成其他细节。在定义注解时，不能继承其他的注解或接口。@interface用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数。方法的名称就是参数的名称，返回值类型就是参数的类型（返回值类型只能是基本类型、Class、String、enum）。可以通过default来声明参数的默认值。

自定义运行是注解大的方面分为两步：一个是申明注解、第二个是解析注解。
#### 申明注解

- 申明注解步骤：

1. 通过@Retention(RetentionPolicy.RUNTIME)元注解确定我们注解是在运行的时候使用。
2. 通过@Target确定我们注解是作用在什么上面的(变量、函数、类等)。
3. 确定我们注解需要的参数

比如下面一段代码我们声明了一个作用在变量上的BindString运行时注解。
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindString {

    int value();

}
```
- 注解解析

运行时注解的解析我们简单的分为三个步骤：

1. 找到类对应的所有属性或者方法(至于是找类的属性还是方法就要看我自定义的注解是定义方法上还是属性上了)。
2. 找到添加了我们注解的属性或者方法。
3. 做我们注解需要自定义的一些操作。

定义注解格式：
```java
public @interface 注解名 {定义体}
```
注解参数的可支持数据类型：
- 所有基本数据类型（int,float,boolean,byte,double,char,long,short)
- String类型
- Class类型
- enum类型
- Annotation类型
- 以上所有类型的数组



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
