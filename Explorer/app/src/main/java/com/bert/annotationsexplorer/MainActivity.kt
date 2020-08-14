package com.bert.annotationsexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import com.bert.annotations.CusAnnotation
import com.bert.annotationsexplorer.R2.layout.activity_main
import com.bert.annotationsexplorer.beans.MsgEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@CusAnnotation(2020)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventBus.getDefault().register(this)

        btn_send_msg?.setOnClickListener {
            EventBus.getDefault().post(MsgEvent())
        }

    }

    @Subscribe
    fun onMsgEvent(event:MsgEvent){

    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}