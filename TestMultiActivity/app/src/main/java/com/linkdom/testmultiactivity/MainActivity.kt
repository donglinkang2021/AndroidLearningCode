package com.linkdom.testmultiactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShowText = findViewById<Button>(R.id.btnShowText)
        btnShowText.setOnClickListener {
            val intent = Intent(this, ShowTextActivity::class.java)
            startActivity(intent)
        }

    }
}