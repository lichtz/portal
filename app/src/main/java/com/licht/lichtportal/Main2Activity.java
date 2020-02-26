package com.licht.lichtportal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.licht.lichtportal.apt.Router$$Group$$user;
import com.licht.router_annotation.KAppId;
import com.licht.router_annotation.Router;
import com.licht.router_annotation.interfaces.RouterLoadPath;


@Router(path = "/app/Main2Activity")
@KAppId(appid = "licht")
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
