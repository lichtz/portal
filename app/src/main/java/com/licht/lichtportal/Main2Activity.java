package com.licht.lichtportal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.licht.lichtportal.apt.Router$$Group$$user;
import com.licht.nuble_framwork.api.core.RouterLoadPath;
import com.licht.router_annotation.KAutor;
import com.licht.router_annotation.Router;


@Router(path = "/app/Main2Activity")
@KAutor(autorName = "licht")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Class<? extends RouterLoadPath> usr = new Router$$Group$$user().loadGroup().get("user");
        if (usr != null) {

        }
    }
}
