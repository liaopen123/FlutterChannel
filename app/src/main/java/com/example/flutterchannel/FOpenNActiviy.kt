package com.example.flutterchannel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flutterchannel.bean.ParamsBean
import kotlinx.android.synthetic.main.activity_f_open_n_activiy.*
import org.greenrobot.eventbus.EventBus

class FOpenNActiviy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_open_n_activiy)
        callFlutter.setOnClickListener {
            EventBus.getDefault().post(ParamsBean("廖鹏辉 加油哦"))
            finish()
        }
    }
}
