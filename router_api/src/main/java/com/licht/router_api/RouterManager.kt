package com.licht.router_api

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.util.LruCache
import com.licht.router_annotation.interfaces.RouterLoadGroup
import com.licht.router_annotation.interfaces.RouterLoadPath
import com.licht.router_annotation.model.RouterBean
import java.util.*


object RouterManager {
    private var group:String? = null
    private var path:String? = null
    private var groupLruCache= LruCache<String,RouterLoadGroup>(163);
    private var  pathLruCache= LruCache<String,RouterLoadPath>(163);

    fun startApp(context: Context, path:String?):Objects?{

        if (TextUtils.isEmpty(path) || !path!!.startsWith("/")) {
            throw  IllegalArgumentException("未按规范配置，如：/app/MainActivity");
        }
        var group = slipGroupFromPath(path);
        val groupClassPath = context.packageName + ".apt" + ".RouterGroup" + group
        Log.i("zyl ","routerGroupClass: "+groupClassPath)
        var cacheGroup = groupLruCache.get(group)
        if (cacheGroup == null){
            val clazz = Class.forName(groupClassPath)
            cacheGroup =clazz.newInstance() as RouterLoadGroup
            groupLruCache.put(group,cacheGroup)
        }

        require(!cacheGroup.loadGroup().isEmpty()){
            "$groupClassPath  hashMap is empty "
        }

        var routerLoadPath = pathLruCache[path]
        if (routerLoadPath ==  null){
            val loadGroup = cacheGroup.loadGroup()[group];
            require(loadGroup != null){
                "not find path"
            }
             routerLoadPath = loadGroup.newInstance()
            pathLruCache.put(path,routerLoadPath)
        }

        val loadPath = routerLoadPath.loadPath()
        require(!loadPath.isEmpty()){
            "not find path"
        }
        val routerBean = loadPath[path]
          if (routerBean != null){
              when(routerBean.type){
                  RouterBean.Type.ACTIVITY -> {
                      val intent = Intent(context,routerBean.getaClass())
                      context.startActivity(intent)
                  }
              }
          }
        return null

    }

    private fun slipGroupFromPath(path: String): String {
        if (path.lastIndexOf("/") == 0) {
            throw  IllegalArgumentException("@ARouter注解未按规范配置，如：/app/MainActivity");
        }
        val finalGroup = path.substring(1, path.indexOf("/", 1))

        require(!TextUtils.isEmpty(finalGroup)) {
            // 架构师定义规范，让开发者遵循
            "@ARouter注解未按规范配置，如：/app/MainActivity"
        }
        return finalGroup

    }
}