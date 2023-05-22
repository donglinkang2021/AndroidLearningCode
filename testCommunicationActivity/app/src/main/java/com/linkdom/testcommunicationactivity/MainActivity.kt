package com.linkdom.testcommunicationactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

//定义信息项的标识值
const val NAME_KEY = "name"
const val AGE_KET = "age"
const val OBJECT_KEY = "user_obj"

//请求码，可以随意指定
const val REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

    lateinit var tvInfo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInfo = findViewById(R.id.tvInfo)

        val btnMainToOther: Button = findViewById(R.id.btnMainToOther)
        btnMainToOther.setOnClickListener {
            val intent = Intent(this, SendToActivity::class.java)
            //打包要传给SendToActivity的信息
            val bundle = Bundle()
            //存入信息
            putMessagesToBundle(bundle)
            putObjectToBundle(bundle)
            //将打包好的信息交给Intent对象
            intent.putExtras(bundle)
            //启动并显示SendToActivity
            startActivity(intent)
        }
        val btnOtherToMain: Button = findViewById(R.id.btnOtherToMain)
        btnOtherToMain.setOnClickListener {
            //启动另一个Acitivity，准备接收从它那里传回的信息
            val intent = Intent(this, ReceiveFromActivity::class.java)
            //启动时，传入一个请求码，用于标识本次请求
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    //存入离散的信息
    fun putMessagesToBundle(bundle: Bundle) {
        bundle.putString(NAME_KEY, "张三")
        bundle.putInt(AGE_KET, 23)
    }

    //保存对象
    fun putObjectToBundle(bundle: Bundle) {
        val user = User("李四", 45)
        bundle.putParcelable(OBJECT_KEY, user)
    }

    //当收到数据时，此方法被回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //如果结果码不是RESTULT_OK，说明对方没有完成相应的工作
        if (resultCode != Activity.RESULT_OK)
            return;
        //依据请求码，对各个请求进行处理
        when (requestCode) {
            //取出对方发回的数据，显示在界面上
            REQUEST_CODE -> tvInfo.text = data?.getStringExtra(MESSAGE_KEY) ?: "无"
        }
    }
}
