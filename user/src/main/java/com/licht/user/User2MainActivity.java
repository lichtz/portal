package com.licht.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.licht.router_annotation.Router;


@Router(path = "/user/User2MainActivity")
public class User2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2_main);
    }
}
