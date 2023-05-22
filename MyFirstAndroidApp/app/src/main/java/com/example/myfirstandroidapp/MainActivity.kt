package com.example.myfirstandroidapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val TAG = "LearnAndroid"
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 开发者可以看见的调试信息
        Log.d(TAG, "这是一条调试信息")
        Log.i(TAG, "这是一条信息")
        Log.w(TAG, "这是一条警告信息")
        Log.e(TAG, "这是一条错误信息")



//        下面为一个region,第一个代码
//        实现了点击按钮时显示当前时间，
//        点击另一个按钮时字体变大，点击另一个按钮时字体变小，
//        长按另一个按钮时字体变大，长按另一个按钮时字体变小，
//        一直按住另一个按钮时字体变大，一直按住另一个按钮时字体变小
//        region
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "Time to change the text!"


        val button = findViewById<Button>(R.id.button)

        // 将文本框的文本对齐方式设为最上方居中
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        button.setOnClickListener {
            textView.text = "" // 清空文本
        }

        var textSize = textView.textSize / resources.displayMetrics.scaledDensity
        textView.textSize = textSize



//        点击按钮，字体逐渐变大
        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            textSize += 1
            Toast.makeText(
                this,
                "textSize0: $textSize",
                Toast.LENGTH_SHORT).show()
            textView.textSize = textSize
            Toast.makeText(
                this,
                "textSize1: ${textView.textSize}}",
                Toast.LENGTH_SHORT).show()
        }

//        点击按钮，字体逐渐变小
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            textSize -= 1
            textView.textSize = textSize
        }
//        region
////        设定长按按钮时，字体变大
//        button3.setOnLongClickListener {
//            textSize += 1
//            textView.textSize = textSize
//            true
//        }
//
////        设定长按另一个按钮时，字体变小
//        button2.setOnLongClickListener {
//            textSize -= 1
//            textView.textSize = textSize
//            true
//        }
//
////        设定一直按住按钮时，字体变大
//        button3.setOnTouchListener { v, event ->
//            textSize += 1
//            textView.textSize = textSize
//            true
//        }
//
////        设定一直按住另一个按钮时，字体变小
//        button2.setOnTouchListener { v, event ->
//            textSize -= 1
//            textView.textSize = textSize
//            true
//        }
//        endregion

    }
}