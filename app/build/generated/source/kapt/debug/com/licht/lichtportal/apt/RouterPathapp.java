package com.licht.lichtportal.apt;

import com.licht.lichtportal.Main2Activity;
import com.licht.lichtportal.Main3Activity;
import com.licht.router_annotation.interfaces.RouterLoadPath;
import com.licht.router_annotation.model.RouterBean;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class RouterPathapp implements RouterLoadPath {
  @Override
  public Map<String, RouterBean> loadPath() {
    Map<String,RouterBean> pathName = new HashMap<>();
     pathName.put("/app/Main2Activity",RouterBean.create(RouterBean.Type.ACTIVITY,Main2Activity.class,"/app/Main2Activity","app")) ;
     pathName.put("/app/Main3Activity",RouterBean.create(RouterBean.Type.ACTIVITY,Main3Activity.class,"/app/Main3Activity","app")) ;
    return pathName;
  }
}
