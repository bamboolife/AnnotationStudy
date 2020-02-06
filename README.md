# Annotation学习和研究

## 现在好多第三方框架都在使用注解的方式来实现。既然都在使用肯定有一定的优势，所以就来研究下，注解的好处和优势。为什么大家都在使用
## 此工程只用来学习、研究和笔记的记录和网上资源的整理
[Android 注解开发断点调试](doc/Android 注解开发断点调试.md)

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

#### 一.自定义运行时注解

运行时注解：在代码运行的过程中通过反射机制找到我们自定义的注解，然后做相应的事情。

> 反射：对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个方法和属性。

自定义运行是注解大的方面分为两步：一个是申明注解、第二个是解析注解。

##### 1. 申明注解

申明注解步骤：

1. 通过@Retention(RetentionPolicy.RUNTIME)元注解确定我们注解是在运行的时候使用。
2. 通过@Target确定我们注解是作用在什么上面的(变量、函数、类等)。
3. 确定我们注解需要的参数

注解参数的可支持数据类型：
- 所有基本数据类型（int,float,boolean,byte,double,char,long,short)
- String类型
- Class类型
- enum类型
- Annotation类型
- 以上所有类型的数组

比如下面一段代码我们声明了一个作用在变量上的BindString运行时注解。
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindString {

    int value();

}
```
##### 2.注解解析

运行时注解的解析我们简单的分为三个步骤：

1. 找到类对应的所有属性或者方法(至于是找类的属性还是方法就要看我自定义的注解是定义方法上还是属性上了)。
2. 找到添加了我们注解的属性或者方法。
3. 做我们注解需要自定义的一些操作。

#### 2.自定义编译时注解

编译时注解就是在编译的过程中用一个javac注解处理器来扫描到我们自定义的注解，生成我们需要的一些文件(通常是java文件)。

自定义编译时注解的步骤：

1. 声明注解。
2. 编写注解处理器。
3. 生成文件(通常是JAVA文件)。

- 声明注解

编译时注解的声明和运行时注解的声明一样也是三步:

1. 通过@Retention(RetentionPolicy.TYPE)元注解确定我们注解是在编译的时候使用。
2. 通过@Target确定我们注解是作用在什么上面的(变量、函数、类等)。
3. 确定我们注解需要的参数。

比如下面的代码我们自定义了一个作用在类上的编译时注解Factory，并且这个注解是需要两个参数的，一个是Class类型，一个是String类型。

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Factory {
    Class type();

    String id();
}
```
- 编写注解处理器

和运行时注解的解析不一样，编译时注解的解析需要我们自己去实现一个注解处理器。

> 注解处理器(Annotation Processor)是javac的一个工具，它用来在编译时扫描和处理注解(Annotation)。一个注解的注解处理器，以Java代码（或者编译过的字节码）作为输入，生成文件(通常是.java文件)作为输出。而且这些生成的Java文件同咱们手动编写的Java源代码一样可以调用。(注意：不能修改已经存在的java文件代码)。

注解处理器所做的工作，就是在代码编译的过程中，找到我们指定的注解。然后让我们更加自己特定的逻辑做出相应的处理(通常是生成JAVA文件)。

注解处理器的写法有固定套路的，两步：

1. 注册注解处理器(这个注解器就是我们第二步自定义的类)。

打包注解处理器的时候需要一个特殊的文件 javax.annotation.processing.Processor 在 META-INF/services 路径下。在javax.annotation.processing.Processor文件里面写上我们自定义注解处理器的全称(包加类的名字)如果有多个注解处理器换行写入就可以。

伟大的google为了方便我们注册注解处理器。给提供了一个注册处理器的库
@AutoService(Processor.class)的注解来简化我们的操作。我们只需要在我们自定义的注解处理器类前面加上google的这个注解，在打包的时候就会自动生成javax.annotation.processing.Processor文件，写入相的信息。不需要我们手动去创建。当然了如果你想使用google的这个注解处理器的库，必须加上下面的依赖。

```java
compile 'com.google.auto.service:auto-service:1.0-rc3'
```

比如下面的这段代码就使用上了google提供的这个注解器处理库，会自动注册注解处理器。

```java
@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {
    ...

}
```

2. 自定义注解处理器类继承AbstractProcessor。

自定义的注解处理器类一定要继承AbstractProcessor，否则找不到我们需要的注解。在这个类里面找到我们需要的注解。做出相应的处理。

关于AbstractProcessor里面的一些函数我们也做一个简单的介绍。

```java
 /**
     * 每个Annotation Processor必须有一个空的构造函数。
     * 编译期间，init()会自动被注解处理工具调用，并传入ProcessingEnvironment参数，
     * 通过该参数可以获取到很多有用的工具类（Element，Filer，Messager等）
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    /**
     * 用于指定自定义注解处理器(Annotation Processor)是注册给哪些注解的(Annotation),
     * 注解(Annotation)指定必须是完整的包名+类名
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    /**
     * 用于指定你的java版本，一般返回：SourceVersion.latestSupported()
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 这相当于每个处理器的主函数main()。你在这里写你的扫描、评估和处理注解的代码，以及生成Java文件.Annotation Processor扫描出的结果会存储进roundEnvironment中，可以在这里获取到注解内容，编写你的操作逻辑。
     * 注意:process()函数中不能直接进行异常抛出,否则程序会异常崩溃
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
```
 注解处理器的核心是process()方法(需要重写AbstractProcessor类的该方法)，而process()方法的核心是Element元素。Element 代表程序的元素，在注解处理过程中，编译器会扫描所有的Java源文件，并将源码中的每一个部分都看作特定类型的Element。它可以代表包、类、接口、方法、字段等多种元素种类。所有Element肯定是有好几个子类。如下所示。

| Element子类	| 解释 |
|:-----------|:----------:
| TypeElement	| 类或接口元素 |
| VariableElement	| 字段、enum常量、方法或构造方法参数、局部变量或异常参数元素 |
| ExecutableElement	| 类或接口的方法、构造方法，或者注解类型元素 |
| PackageElement	| 包元素 |
| TypeParameterElement	| 类、接口、方法或构造方法元素的泛型参数 |

关于Element类里面的方法我们也做一个简单的介绍：
```java
/**
     * 返回此元素定义的类型，int,long这些
     */
    TypeMirror asType();

    /**
     * 返回此元素的种类:包、类、接口、方法、字段
     */
    ElementKind getKind();

    /**
     * 返回此元素的修饰符:public、private、protected
     */
    Set<Modifier> getModifiers();

    /**
     * 返回此元素的简单名称(类名)
     */
    Name getSimpleName();

    /**
     * 返回封装此元素的最里层元素。
     * 如果此元素的声明在词法上直接封装在另一个元素的声明中，则返回那个封装元素；
     * 如果此元素是顶层类型，则返回它的包；
     * 如果此元素是一个包，则返回 null；
     * 如果此元素是一个泛型参数，则返回 null.
     */
    Element getEnclosingElement();

    /**
     * 返回此元素直接封装的子元素
     */
    List<? extends Element> getEnclosedElements();

    /**
     * 返回直接存在于此元素上的注解
     * 要获得继承的注解，可使用 getAllAnnotationMirrors
     */
    List<? extends AnnotationMirror> getAnnotationMirrors();

    /**
     * 返回此元素上存在的指定类型的注解
     */
```

自定义处理器的过程中除了要了解Element类和他的子类的用法，还有四个帮助类也是需要了解的。Elements、Types、Filer、Messager。

| 注解解析器帮助类	| 解释 |
|:----------------|:-----------------|
| Elements	| 一个用来处理Element的工具类 |
| Types	 | 一个用来处理TypeMirror的工具类 |
| Filer	| 用于创建文件(比如创建class文件) |
| Messager	| 用于输出，类似printf函数 |

这四个帮助类都可以在init()函数里面通过ProcessingEnvironment获取到。类似如下的代码获取

```java
@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {

    /**
     * 用来处理TypeMirror的工具类
     */
    private Types                              mTypeUtils;
    /**
     * 用于创建文件
     */
    private Filer                              mFiler;
    /**
     * 用于打印信息
     */
    private Messager                           mMessager;
    ...

    /**
     * 获取到Types、Filer、Messager、Elements
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mTypeUtils = processingEnvironment.getTypeUtils();
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        ...
    }

    ...


}
```
- 生成文件

生成文件，通常是生成一个java文件。直接调用帮助类Filer的createSourceFile()函数就可以创建一个java文件。之后就是在这个java文件里面写入我们需要的内容了。为了提高大家的开发效率推荐两个写java源文件的开源库FileWriter和JavaPoet。两个库用起来也很简单，这里我们就不深入进去了。生成文件这一部分的内容非常的简答。具体可以参考我们下编译时注解实例。

> JavaWrite是JavaPoet增强版。

![image](https://github.com/bamboolife/AnnotationStudy/blob/master/image/annotation_flow.png)




