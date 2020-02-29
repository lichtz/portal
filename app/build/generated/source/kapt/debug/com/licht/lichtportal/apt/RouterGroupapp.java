package com.licht.lichtportal.apt;

import com.licht.router_annotation.interfaces.RouterLoadGroup;
import com.licht.router_annotation.interfaces.RouterLoadPath;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class RouterGroupapp implements RouterLoadGroup {
  @Override
  public Map<String, Class<? extends RouterLoadPath>> loadGroup() {
    Map<String, Class<? extends RouterLoadPath>> groupMap = new HashMap<>();
    groupMap.put("app", RouterPathapp.class);
    return groupMap;
  }
}
