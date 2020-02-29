package com.licht.lichtportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.licht.router_annotation.KAppId;
import com.licht.router_annotation.Parameter;
import com.licht.router_annotation.Router;
import com.licht.router_api.ParameterManager;

@KAppId(appid = "AA")
@Router(path = "/app/Main3Activity")
public class Main3Activity extends AppCompatActivity {

    @Parameter(name = "bg")
    public String xc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ParameterManager.INSTANCE.loadParameter(this);
        Log.i("zyl",xc);


    }

    public void jp(View view) {
//        Class findTargetClass = XRUO.findTargetClass("Main2Activity");
//        startActivity(new Intent(this,findTargetClass));
    }
}
