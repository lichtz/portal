package com.licht.lichtportal

import com.licht.router_annotation.interfaces.BundleAppid

class BundleAppidApp : BundleAppid {
    override fun getAppids(): HashMap<String, String> {
        var appids =HashMap<String,String>();
        appids.put("app","21111");
        return appids;
    }

}