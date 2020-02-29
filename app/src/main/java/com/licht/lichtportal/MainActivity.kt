package com.licht.lichtportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.licht.router_annotation.KAppId

@KAppId(appid = "xiaozi")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun jpx(view: View) {
        var intent = Intent(this,Main3Activity::class.java)
        intent.putExtra("bg","hello")
        startActivity(intent )
    }
}
