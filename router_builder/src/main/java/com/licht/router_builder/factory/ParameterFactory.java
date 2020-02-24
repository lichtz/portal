package com.licht.router_builder.factory;

import com.licht.router_annotation.Parameter;
import com.licht.router_builder.utils.Constants;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class ParameterFactory {
    private static final String CONTENT = "$T t = ($T)target";
    private MethodSpec.Builder methodBuilder;
    private Messager messager;
    private Types typeUtils;

    private ClassName className;
    // 方法体构建
    private MethodSpec.Builder methodBuidler;
    private ParameterFactory(Builder builder){
        this.messager = builder.messager;
        this.className = builder.className;
    methodBuidler = MethodSpec.methodBuilder(Constants.PARAMETER_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(builder.parameterSpec);
    }
    public void addFirstStatement() {
        // 方法内容：MainActivity t = (MainActivity) target;
        methodBuidler.addStatement(CONTENT, className, className);
    }

    public MethodSpec build() {
        return methodBuidler.build();
    }

    public void buildStatement(Element element) {
        TypeMirror typeMirror = element.asType();
        int type = typeMirror.getKind().ordinal();
        String fieldName = element.getSimpleName().toString();
        String annotationValue = element.getAnnotation(Parameter.class).name();
        annotationValue = (annotationValue == null || annotationValue.length() == 0)?fieldName:annotationValue;
        String finalValue = "t."+fieldName;
        String methodContent = finalValue+"= t.getIntent().";
        if (type == TypeKind.INT.ordinal()) {
            // t.s = t.getIntent().getIntExtra("age", t.age);
            methodContent += "getIntExtra($S, " + finalValue + ")";
        } else if (type == TypeKind.BOOLEAN.ordinal()) {
            // t.s = t.getIntent().getBooleanExtra("isSuccess", t.age);
            methodContent += "getBooleanExtra($S, " + finalValue + ")";
        } else {
            // t.s = t.getIntent.getStringExtra("s");
            if (typeMirror.toString().equalsIgnoreCase(Constants.STRING)) {
                methodContent += "getStringExtra($S)";
            }
        }
        // 健壮代码
        if (methodContent.endsWith(")")) {
            // 添加最终拼接方法内容语句
            methodBuidler.addStatement(methodContent, annotationValue);
        } else {
            messager.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参");
        }


    }

    public static class Builder {

        // Messager用来报告错误，警告和其他提示信息
        private Messager messager;

        // 类名，如：MainActivity
        private ClassName className;

        // 方法参数体
        private ParameterSpec parameterSpec;

        public Builder(ParameterSpec parameterSpec) {
            this.parameterSpec = parameterSpec;
        }

        public Builder setMessager(Messager messager) {
            this.messager = messager;
            return this;
        }

        public Builder setClassName(ClassName className) {
            this.className = className;
            return this;
        }

        public ParameterFactory build() {
            if (parameterSpec == null) {
                throw new IllegalArgumentException("parameterSpec方法参数体为空");
            }

            if (className == null) {
                throw new IllegalArgumentException("方法内容中的className为空");
            }

            if (messager == null) {
                throw new IllegalArgumentException("messager为空，Messager用来报告错误、警告和其他提示信息");
            }

            return new ParameterFactory(this);
        }
    }
}
