package com.licht.router_builder.utils;

public class Constants {
    public static final String ROUTER_ANNOTION_TYPES="com.licht.router_annotation.Router";
    public static final String MODULE_NAME="moduleName";
    public static final String PACKAGENAME="packageNameForAPT";
    public static final String ACTIVITY = "android.app.Activity";
    public static final String STRING = "java.lang.String";
    public static final String BASE_PACKAGE = "com.licht.nuble_framwork.api";
    public static final String ROUTE_GROUP_INTERFACE =Constants.BASE_PACKAGE+".core.RouterLoadGroup";
    public static final String ROUTE_PATH_INTERFACE =Constants.BASE_PACKAGE+".core.RouterLoadPath";
    public static final String PATH_LOAD_NAME ="loadPath";
    public static final String PATH_PARAMEYER_NAME ="pathName";
    public static final String GROUP_FILE_NAME = "Router$$Group$$";
    public static final String PATH_FILE_NAME = "Router$$Path$$";
    public static final String GROUP_METHOD_NAME = "loadGroup";
    public static final String GROUP_PARAMETER_NAME = "groupMap";

    public static final String PARAMETER_ANNOTION_TYPES="com.licht.router_annotation.Parameter";
    public static final String PARAMETER_PATH_INTERFACE =Constants.BASE_PACKAGE+".core.ParameterLoad";
    public static final String PARAMETER_NAME="target";
    public static final String PARAMETER_METHOD_NAME = "loadParameter";
    public static final String PARAMETER_FILE_NAME = "$$Parameter";

}
