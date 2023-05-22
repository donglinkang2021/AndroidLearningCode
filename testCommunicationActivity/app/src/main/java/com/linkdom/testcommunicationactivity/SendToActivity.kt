package com.linkdom.testcommunicationactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SendToActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_to)

        //在Activity中，可以直接从它的intent属性中提取出外部传入的数据
        val name = intent.getStringExtra(NAME_KEY)
        val age = intent.getIntExtra(AGE_KET, 0)
        val user = intent.getParcelableExtra<User>(OBJECT_KEY)
        val tvInfo: TextView = findViewById(R.id.tvInfo)
        tvInfo.text = "姓名：$name, 年龄：$age\n" +
                "User(${user!!.name}，${user!!.age})"

    }
}
