package com.licht.router_annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class KAutor (val autorName:String="佚名")