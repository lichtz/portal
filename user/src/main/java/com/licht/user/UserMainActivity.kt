package com.licht.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.licht.router_annotation.KAppId
import com.licht.router_annotation.Router
import com.licht.router_api.RouterManager

@KAppId(appid="200011")
@Router(path = "/user/UserMainActivity")
class UserMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

    }

    fun jp(view: View) {
        RouterManager.startApp(this,"/app/Main2Activity")
    }
}
