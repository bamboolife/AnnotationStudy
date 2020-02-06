package com.sundy.test_compiler;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

/**
 * 因为我们项目是很大的，可能多个地方用到工厂模式(多个工厂)。
 * People -> (Male、Female)   对应一个FactoryGroupedClasses mItemsMap变量里面有两个元素Male和Female
 * Shape -> (Triangle、Rectangle)  对应一个FactoryGroupedClasses mItemsMap变量里面有两个元素Triangle和Rectangle
 */
public class FactoryGroupedClasses {

	/**
	 * 将被添加到生成的工厂类的名字中
	 */
	private static final String SUFFIX = "Factory";

	/**
	 * 父类的完整路径
	 */
	private String                             mQualifiedClassName;
	/**
	 * 同一个工程里面所有
	 */
	private Map<String, FactoryAnnotatedClass> mItemsMap;

	public FactoryGroupedClasses(String qualifiedClassName) {
		mQualifiedClassName = qualifiedClassName;
		mItemsMap = new LinkedHashMap<>();
	}

	/**
	 * 添加元素，要判断Factory注解的id，是否重复（同一个工厂的元素都会添加到里面来）
	 */
	public void add(FactoryAnnotatedClass factoryAnnotate) throws IllegalArgumentException {
		FactoryAnnotatedClass existing = mItemsMap.get(factoryAnnotate.getId());
		if (existing != null) {
			throw new IllegalArgumentException(factoryAnnotate.getId() + " existing");
		}
		mItemsMap.put(factoryAnnotate.getId(), factoryAnnotate);
	}

	/**
	 * 生成java源文件代码
	 */
	public void generateCode(Elements elementUtils, Filer filer) throws IOException {
		TypeElement superClassName = elementUtils.getTypeElement(mQualifiedClassName);
		String factoryClassName = superClassName.getSimpleName() + SUFFIX;

		method2(elementUtils, filer, superClassName, factoryClassName);
		//method1(elementUtils, filer, superClassName, factoryClassName);
		return;
	}

	private void method2(Elements elementUtils, Filer filer, TypeElement superClassName, String factoryClassName) throws IOException {
		PackageElement pkg=elementUtils.getPackageOf(superClassName);
		String packageName=pkg.isUnnamed()?"":pkg.getQualifiedName().toString();

		MethodSpec.Builder method= MethodSpec.methodBuilder("create")
				.addModifiers(Modifier.PUBLIC)
				.addParameter(String.class,"id")
				.returns(TypeName.get(superClassName.asType()));

		method.beginControlFlow("if (id == null)")
				.addStatement("throw new IllegalArgumentException($S)", "id is null!")
				.endControlFlow();


		for (FactoryAnnotatedClass item : mItemsMap.values()) {
			method.beginControlFlow("if ($S.equals(id))", item.getId())
					.addStatement("return new $L()", item.getTypeElement().getQualifiedName().toString())
					.endControlFlow();
		}

		method.addStatement("throw new IllegalArgumentException($S + id)", "Unknown id = ");

		TypeSpec typeSpec = TypeSpec.classBuilder(factoryClassName).addModifiers(Modifier.PUBLIC).addMethod(method.build()).build();

		// Write file
		JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
	}

	private void method1(Elements elementUtils, Filer filer, TypeElement superClassName, String factoryClassName) throws IOException {
		JavaFileObject jfo = filer.createSourceFile(mQualifiedClassName + SUFFIX);
		Writer writer = jfo.openWriter();
		JavaWriter jw = new JavaWriter(writer);

		// 写包名
		PackageElement pkg = elementUtils.getPackageOf(superClassName);
		if (!pkg.isUnnamed()) {
			jw.emitPackage(pkg.getQualifiedName().toString());
			jw.emitEmptyLine();
		} else {
			jw.emitPackage("");
		}

		jw.beginType(factoryClassName, "class", EnumSet.of(Modifier.PUBLIC));
		jw.emitEmptyLine();
		jw.beginMethod(mQualifiedClassName, "create", EnumSet.of(Modifier.PUBLIC), "String", "id");

		jw.beginControlFlow("if (id == null)");
		jw.emitStatement("throw new IllegalArgumentException(\"id is null!\")");
		jw.endControlFlow();

		for (FactoryAnnotatedClass item : mItemsMap.values()) {
			jw.beginControlFlow("if (\"%s\".equals(id))", item.getId());
			jw.emitStatement("return new %s()", item.getTypeElement().getQualifiedName().toString());
			jw.endControlFlow();
			jw.emitEmptyLine();
		}

		jw.emitStatement("throw new IllegalArgumentException(\"Unknown id = \" + id)");
		jw.endMethod();
		jw.endType();
		jw.close();
	}

}

