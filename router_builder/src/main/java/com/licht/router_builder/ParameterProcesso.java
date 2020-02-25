package com.licht.router_builder;

import com.licht.router_annotation.Parameter;
import com.licht.router_builder.factory.ParameterFactory;
import com.licht.router_builder.utils.Constants;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({Constants.PARAMETER_ANNOTION_TYPES})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParameterProcesso extends AbstractProcessor {
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Types typeUtils;
    private Map<TypeElement, List<Element>> tempParameterMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations != null && annotations.size() > 0) {
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Parameter.class);
            if (elementsAnnotatedWith != null && elementsAnnotatedWith.size() > 0) {
                valueofParameterMap(elementsAnnotatedWith);
                try {
                    createParameterFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    private void createParameterFile() throws IOException {
        if (tempParameterMap.size() == 0) {
            return;
        }
        //获取改接口element类型
        TypeElement activityType = elementUtils.getTypeElement(Constants.ACTIVITY);
        TypeElement parameterType = elementUtils.getTypeElement(Constants.PARAMETER_PATH_INTERFACE);
        ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.OBJECT,Constants.PARAMETER_NAME).build();
        for (Map.Entry<TypeElement, List<Element>> typeElementListEntry : tempParameterMap.entrySet()) {
            TypeElement key = typeElementListEntry.getKey();
            if (!typeUtils.isSubtype(key.asType(),activityType.asType())){
                throw new RuntimeException("@Parameter注解目前仅限用于Activity类之上");
            }

            ClassName className = ClassName.get(key);
            ParameterFactory factory = new ParameterFactory.Builder(parameterSpec)
                    .setMessager(messager)
                    .setClassName(className)
                    .build();
            factory.addFirstStatement();
            for (Element element : typeElementListEntry.getValue()) {
                factory.buildStatement(element);
            }
            String finalClassName = key.getSimpleName() + Constants.PARAMETER_FILE_NAME;
            messager.printMessage(Diagnostic.Kind.NOTE, "APT生成获取参数类文件：" +
                    className.packageName() + "." + finalClassName);

            JavaFile.builder(className.packageName(), TypeSpec.classBuilder(finalClassName).addSuperinterface(ClassName.get(parameterType))
                    .addModifiers(Modifier.PUBLIC).addMethod(factory.build()).build()).build().writeTo(filer);


        }

    }

    private void valueofParameterMap(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            if (tempParameterMap.containsKey(typeElement)) {
                tempParameterMap.get(typeElement).add(element);
            } else {
                List<Element> fields = new ArrayList<>();
                fields.add(element);
                tempParameterMap.put(typeElement, fields);
            }
        }
    }
}
