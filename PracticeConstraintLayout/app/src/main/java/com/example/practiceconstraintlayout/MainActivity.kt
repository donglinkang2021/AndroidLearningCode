package com.example.practiceconstraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo0)


        // 自动滚动到最右边
//        val scrollView: HorizontalScrollView = findViewById(R.id.horizontalScrollView)
//        scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)


        val tv: TextView = findViewById(R.id.textView)
        tv.text = generateLongTextHorizontal(100)
        val tv2: TextView = findViewById(R.id.textView2)
        tv2.text = generateLongTextVertical(100)


    }

    private fun generateLongTextHorizontal(num: Int): CharSequence? {
        val sb = StringBuffer()
        for (i in 1..num) {
            sb.append("第${i}列 ")
        }
        return sb.toString()
    }

    private fun generateLongTextVertical(num: Int): CharSequence? {
        val sb = StringBuffer()
        for (i in 1..num) {
            sb.append("第${i}行\n")
        }
        return sb.toString()
    }
}