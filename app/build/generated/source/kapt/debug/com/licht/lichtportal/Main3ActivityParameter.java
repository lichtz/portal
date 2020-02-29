package com.licht.lichtportal;

import com.licht.router_annotation.interfaces.ParameterLoad;
import java.lang.Object;
import java.lang.Override;

public class Main3ActivityParameter implements ParameterLoad {
  @Override
  public void loadParameter(Object target) {
    Main3Activity t = (Main3Activity)target;
    t.xc= t.getIntent().getStringExtra("bg");
  }
}
