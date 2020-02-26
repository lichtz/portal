package com.licht.router_builder

import com.licht.router_annotation.KAppId
import com.licht.router_annotation.model.RouterBean
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@SupportedOptions("bundleName", "appId")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.licht.router_annotation.KAppId")
class KRouterManagerProcesso : AbstractProcessor() {

    private val appidsTemp: Map<String, List<String>> =
        HashMap()
//    private val tempGroupMap: Map<String, String> =
//        HashMap()


    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val pageName = processingEnv.options.get("packageNameForAPT")
        val bundleName = processingEnv.options.get("bundleName")
        if (pageName == null || pageName.length == 0 || bundleName == null || bundleName.length == 0) {
            throw  NullPointerException("kapt -> arguments is null")
        }
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING,"\n\n"+bundleName+">>>>>$pageName");

        val elementsAnnotatedWith = roundEnv!!.getElementsAnnotatedWith(KAppId::class.java)
//        elementsAnnotatedWith.forEachIndexed { index, element ->
//            appid.

//        }

//        val packageName = processingEnv.elementUtils.getPackageOf(element)
//        var annotation: KAppId = element.getAnnotation(KAppId::class.java)
//        val createClassFile = processingEnv.filer.createClassFile("$packageName.APT.BundleAppid${bundleName}")
//        val openWriter = createClassFile.openWriter()
//        openWriter.write("nimeide")
//        openWriter.close()
        return false
    }

}