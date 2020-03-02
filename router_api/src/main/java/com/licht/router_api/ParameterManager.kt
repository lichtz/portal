package com.licht.router_api

import android.app.Activity
import android.util.LruCache
import com.licht.router_annotation.interfaces.ParameterLoad


object ParameterManager{
   private var cache:LruCache<String,ParameterLoad> = LruCache(168);
    private val FILE_SUFFIX_NAME="Parameter"

    fun loadParameter(activity: Activity){
        val name = activity::class.java.name
        var parameterLoad = cache.get(name)

        if (parameterLoad == null){
            val clazz   = Class.forName(name + FILE_SUFFIX_NAME)
            parameterLoad = clazz.newInstance() as ParameterLoad
            cache.put(name,parameterLoad)
        }
        parameterLoad.loadParameter(activity);
    }


}