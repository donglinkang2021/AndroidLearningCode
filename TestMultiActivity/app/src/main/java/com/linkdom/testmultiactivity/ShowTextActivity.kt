package com.linkdom.testmultiactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class ShowTextActivity: AppCompatActivity(){
    lateinit var textView: TextView


    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var text: String
    private var textSize by Delegates.notNull<Float>()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        text = textView.text.toString()
        outState.putString("text",text)
        outState.putFloat("textSize",textSize)
    }



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showtext_scrollable)


        textView= findViewById(R.id.textView)
        textSize = textView.textSize / resources.displayMetrics.scaledDensity
        textView.textSize = textSize

        // 注册回调函数
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val text = it.data?.data?.let { it1 -> readText(it1) }
                text?.let {
                    textView.text = text
                }
            }
        }

        // choose txt file when click button
        val btnChooseTxtFile = findViewById<Button>(R.id.btnChooseTxt)
        btnChooseTxtFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "text/plain"
            val chooser = Intent.createChooser(intent, "选择一个文本文件")
            launcher.launch(chooser)
        }

        val btnPlusTextView = findViewById<Button>(R.id.btnPlusTextSize)
        btnPlusTextView.setOnClickListener {
            textSize += 1
            textView.textSize = textSize
        }

        val btnMinusTextView = findViewById<Button>(R.id.btnMinusTextSize)
        btnMinusTextView.setOnClickListener {
            textSize -= 1
            textView.textSize = textSize
        }

        // 退出app
        val btnExit = findViewById<Button>(R.id.btnExit)
        btnExit.setOnClickListener {

        }



        if (savedInstanceState != null) {
            text = savedInstanceState.getString("text")!!
            textView.text = text
            textSize = savedInstanceState.getFloat("textSize")
            textView.textSize = textSize
        }

    }

    // 读取指定文件的内容
    @SuppressLint("Recycle")
    private fun readText(data: Uri): String? {
        return try {
            contentResolver.openInputStream(data)?.bufferedReader().use {
                it?.readText()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}