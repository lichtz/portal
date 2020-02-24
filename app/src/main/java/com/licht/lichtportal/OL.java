package com.licht.lichtportal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OL {
    private Class aClass;
    private static Map<String, HashMap<String,Boolean>> map = new HashMap<>();
    public static void ss(){
        HashMap<String, Boolean> stringBooleanHashMap = map.get("!");
        if (stringBooleanHashMap == null){
            stringBooleanHashMap = new HashMap<>();
            stringBooleanHashMap.put("!",false);
        }else {
            stringBooleanHashMap.get("A");
        }
    }
}
