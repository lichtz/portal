package com.licht.lichtportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.licht.lichtportal.apt.RouterGroupuser
import com.licht.lichtportal.apt.RouterPathuser
import com.licht.router_annotation.KAppId
import com.licht.router_api.RouterManager
import kotlin.math.log

@KAppId(appid = "xiaozi")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun jpx(view: View) {
      RouterManager.startApp(this,"/user/UserMainActivity")
    }
}
