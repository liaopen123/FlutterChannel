package com.example.flutterchannel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flutterchannel.bean.ParamsBean
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class FlutterHostActivity : FlutterActivity(),AnkoLogger{
    private  var methodChannel: MethodChannel?= null
    val CHANNEL_NAME = "com.example.flutterchannel"
    val EVENT_CHANNEL_NAME = "com.example.flutterchannel/eventChannel"
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        initMethodChannel(flutterEngine)
        initEventChannel(flutterEngine)

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    private fun initMethodChannel(flutterEngine: FlutterEngine) {
         methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_NAME)
        methodChannel?.setMethodCallHandler { call, result -> dispatcherCall(call,result) }
    }

    private fun dispatcherCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method){
            "没有结果的flutterCallNative"->{
                val params = call.argument<Any>("params") as String?
                toast("得到来自flutter传来的 参数:$params")
            }
            "有返回值的flutterCallNative" ->{
                val params = call.argument<Any>("params") as String?
                info { "得到来自flutter传来的 参数:$params,并返回给flutter" }
                val list = listOf("星期一", "廖鹏辉", "雪雪")
                result.success(list)
            }
            "打开一个新的activity"->{
                startActivity(Intent(this@FlutterHostActivity,FOpenNActiviy::class.java))
            }
        }
    }


    private fun initEventChannel(flutterEngine: FlutterEngine) {
        val eventChannel =
            EventChannel(flutterEngine.dartExecutor.binaryMessenger, EVENT_CHANNEL_NAME)
        eventChannel.setStreamHandler(object:EventChannel.StreamHandler{
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
              info { "Android EventChannel onListen called" }
                events?.success("发送成功的event_channel通知23333")
            }

            override fun onCancel(arguments: Any?) {
                info { "Android EventChannel onCancel called" }
            }

        })
    }






    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun sentParams2Flutter(params: ParamsBean) {
        params.name
        methodChannel!!.invokeMethod("callFlutter",params.name,object :MethodChannel.Result{
            override fun notImplemented() {
                info { "sentParams2Flutter notImplemented" }
            }
            override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                info { "sentParams2Flutter error:$errorMessage" }
            }
            override fun success(result: Any?) {
                info { "sentParams2Flutter success" }
            }
        })

    }

}
