package com.licht.lichtportal


import com.licht.router_annotation.interfaces.AppidResource
import com.licht.router_annotation.interfaces.BundleAppid
import java.util.*

class AppIdResourceApp : AppidResource {
    override fun getBundleAppids(): BundleAppid {
        val pathName: Map<String, String> = HashMap()

        return  BundleAppidApp()
    }

}