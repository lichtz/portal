package com.licht.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.licht.router_annotation.KAppId

@KAppId(appid="200011")
class UserMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
    }
}
