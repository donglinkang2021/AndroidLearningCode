package com.linkdom.testcommunicationactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

val MESSAGE_KEY = "message_key"

class ReceiveFromActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_from)
        val btnReturnToMain: Button = findViewById(R.id.btnReturnToMain)
        val edtUserInput: EditText = findViewById(R.id.edtUserInput)
        btnReturnToMain.setOnClickListener {
            val data = Intent()
            //将要传回的信息放入Intent中
            data.putExtra(MESSAGE_KEY, edtUserInput.text.toString())
            //设置结果码
            setResult(Activity.RESULT_OK, data)
            //销毁自己，重新显示MainActivity
            finish()
        }
    }
}
