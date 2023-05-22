package com.linkdom.readbook

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linkdom.readbook.ui.BookReadScreen
import com.linkdom.readbook.ui.theme.ReadBookTheme

class BookReadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ReadBookTheme {
                val activity = LocalContext.current as Activity
                val bookid = activity.intent.getStringExtra("bookID")?:""
                BookReadScreen(bookid)
            }
        }
    }
}
