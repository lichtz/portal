package com.licht.router_annotation.interfaces;


import com.licht.router_annotation.model.RouterBean;

import java.util.Map;

public interface RouterLoadPath {
    public Map<String, RouterBean> loadPath();

}
