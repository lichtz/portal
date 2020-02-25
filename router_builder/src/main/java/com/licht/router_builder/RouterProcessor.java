package com.licht.router_builder;

import com.licht.router_annotation.Router;
import com.licht.router_annotation.model.RouterBean;
import com.licht.router_builder.utils.Constants;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

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
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

//@AutoService(Processor.class)
@SupportedAnnotationTypes({Constants.ROUTER_ANNOTION_TYPES})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions({Constants.MODULE_NAME, Constants.PACKAGENAME})
public class RouterProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Types typeUtils;

    private String packageName;
    private String moduleName;

    private Map<String, List<RouterBean>> tempPathMap = new HashMap<>();
    private Map<String, String> tempGroupMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        Map<String, String> options = processingEnv.getOptions();
        if (options != null) {
            moduleName = options.get(Constants.MODULE_NAME);
            packageName = options.get(Constants.PACKAGENAME);
            messager.printMessage(Diagnostic.Kind.NOTE, "packageName: " + packageName + "moduleName： " +
                    moduleName);
        }
        if (moduleName == null || packageName == null) {
            throw new RuntimeException("missing apt option  set javaCompileOptions-> annotationProcessorOptions" +
                    "-> arguments = [ moduleName:\"\",packageNameForAPT:\"\"");
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!isEmpty(annotations)) {
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Router.class);
            if (!isEmpty(elementsAnnotatedWith)) {
                try {
                    parseElements(elementsAnnotatedWith);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    private void parseElements(Set<? extends Element> elements) throws IOException {
        TypeElement typeElement = elementUtils.getTypeElement(Constants.ACTIVITY);
        TypeMirror activityMirror = typeElement.asType();
        for (Element e : elements
        ) {
            TypeMirror typeMirror = e.asType();
            messager.printMessage(Diagnostic.Kind.NOTE, "apt element ：" + typeMirror.toString());
            Router annotation = e.getAnnotation(Router.class);
            RouterBean routerBean = new RouterBean.Builder().setGroup(annotation.group()).setPath(annotation.path())
                    .setElement(e).build();
            if (typeUtils.isSubtype(typeMirror, activityMirror)) {
                routerBean.setType(RouterBean.Type.ACTIVITY);

            } else {
                throw new RuntimeException("Router只支持Activity");
            }
            valueOfPathMap(routerBean);
        }

        TypeElement groupLoadType = elementUtils.getTypeElement(Constants.ROUTE_GROUP_INTERFACE);
        TypeElement pathType = elementUtils.getTypeElement(Constants.ROUTE_PATH_INTERFACE);
        createPathFile(pathType);
        createGroupFile(groupLoadType, pathType);

    }

    private void createGroupFile(TypeElement groupLoadType, TypeElement pathType) throws IOException {
        if (tempGroupMap == null || tempGroupMap.isEmpty()) {
            return;
        }
        ParameterizedTypeName returnType = ParameterizedTypeName.get(ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))));
        MethodSpec.Builder method = MethodSpec.methodBuilder(Constants.GROUP_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType);
        method.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class)
                , ClassName.get(String.class)
                , ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathType)))
                , Constants.GROUP_PARAMETER_NAME
                , ClassName.get(HashMap.class));

        for (Map.Entry<String, String> entry : tempGroupMap.entrySet()) {
            method.addStatement("$N.put($S, $T.class)",
                    Constants.GROUP_PARAMETER_NAME, // groupMap.put
                    entry.getKey(),
                    ClassName.get(packageName, entry.getValue()));
        }
        method.addStatement("return $N", Constants.GROUP_PARAMETER_NAME);
        // 生成类文件：ARouter$$Group$$app
        String finalClassName = Constants.GROUP_FILE_NAME + moduleName;
        JavaFile.builder(packageName,
                TypeSpec.classBuilder(finalClassName)
                        .addSuperinterface(ClassName.get(groupLoadType))
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(method.build())
                        .build())
                .build()
                .writeTo(filer);

    }

    private void createPathFile(TypeElement pathType) throws IOException {
        if (tempPathMap.size() == 0) {
            return;
        }
        //返回值Map<String,RouterBean>
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class)
                , ClassName.get(RouterBean.class));
        for (Map.Entry<String, List<RouterBean>> listEntry : tempPathMap.entrySet()) {
            MethodSpec.Builder method = MethodSpec.methodBuilder(Constants.PATH_LOAD_NAME).addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(parameterizedTypeName);
            method.addStatement("$T<$T,$T> $N = new $T<>()", ClassName.get(Map.class)

                    , ClassName.get(String.class)
                    , ClassName.get(RouterBean.class)
                    , Constants.PATH_PARAMEYER_NAME
                    , ClassName.get(HashMap.class));
            List<RouterBean> value = listEntry.getValue();
            for (RouterBean routerBean : value) {
                //$T.$L   RouterBean.Type.ACTIVITY
                method.addStatement(" $N.put($S,$T.create($T.$L,$T.class,$S,$S)) "
                        , Constants.PATH_PARAMEYER_NAME
                        , routerBean.getPath()
                        , ClassName.get(RouterBean.class)
                        , ClassName.get(RouterBean.Type.class)
                        , routerBean.getType()
                        , ClassName.get((TypeElement) routerBean.getElement())
                        , routerBean.getPath()
                        , routerBean.getGroup());
            }
            method.addStatement("return $N", Constants.PATH_PARAMEYER_NAME);
            String finalClassName = Constants.PATH_FILE_NAME + listEntry.getKey();
            messager.printMessage(Diagnostic.Kind.NOTE, "APT create file" + packageName + "." + finalClassName);
            JavaFile.builder(packageName, TypeSpec.classBuilder(finalClassName)
                    .addSuperinterface(ClassName.get(pathType))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(method.build())
                    .build())
                    .build()
                    .writeTo(filer);
            tempGroupMap.put(listEntry.getKey(), finalClassName);

        }

    }


    private void valueOfPathMap(RouterBean routerBean) {
        if (!chrckRouterPath(routerBean)) {
            return;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean: " + routerBean.toString());
        List<RouterBean> routerBeanList = tempPathMap.get(routerBean.getGroup());
        if (routerBeanList == null) {
            routerBeanList = new ArrayList<>();
            routerBeanList.add(routerBean);
            tempPathMap.put(routerBean.getGroup(), routerBeanList);
        } else {
            routerBeanList.add(routerBean);
        }

    }

    private boolean chrckRouterPath(RouterBean routerBean) {
        String path = routerBean.getPath();
        String group = routerBean.getGroup();

        if (isEmpty(routerBean.getPath()) || !routerBean.getPath().startsWith("/") || path.lastIndexOf("/") == 0) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@Router ex:/app/MainActivity");
            return false;
        }

        if (!isEmpty(group)) {
            if (!group.equals(moduleName)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "@Router ex:/app/MainActivity  app需与子模块名称一致 ");
                return false;

            } else {
                routerBean.setGroup(group);
            }
        } else {
            String substringGroup = path.substring(1, path.indexOf("/", 1));
            if (substringGroup.contains("/")) {
                messager.printMessage(Diagnostic.Kind.ERROR, "@Router ex:/app/MainActivity");
                return false;
            }
            routerBean.setGroup(substringGroup);
        }


        return true;
    }

    public boolean isEmpty(Set set) {
        return set == null || set.size() == 0;
    }

    public boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
