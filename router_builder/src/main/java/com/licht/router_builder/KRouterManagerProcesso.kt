package com.licht.router_builder

import com.licht.router_annotation.KAutor
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@SupportedOptions("gg","dd")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.licht.router_annotation.KAutor")
class KRouterManagerProcesso : AbstractProcessor(){


    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val elementsAnnotatedWith = roundEnv!!.getElementsAnnotatedWith(KAutor::class.java)

        elementsAnnotatedWith.forEachIndexed { index, element ->
            val packageOf = processingEnv.elementUtils.getPackageOf(element)
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING,"$index "+packageOf.qualifiedName)
        }
        return false
    }

}