package com.bert.annotationsexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bert.annotations.CusAnnotation

@CusAnnotation(2020)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}