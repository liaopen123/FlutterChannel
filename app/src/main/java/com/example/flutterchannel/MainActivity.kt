package com.example.flutterchannel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jump_flutter_activity.setOnClickListener {
            startActivity(FlutterActivity.createDefaultIntent(this))
        }
        jump_flutter_channel_activity.setOnClickListener {
            startActivity(FlutterActivity
                .withNewEngine()
                .initialRoute("first")
                .build(this@MainActivity)
                .setClass(this@MainActivity, FlutterHostActivity::class.java))

        }

    }
}
