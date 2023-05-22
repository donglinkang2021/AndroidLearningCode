package com.linkdom.readbook.fileio.readFile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader


@Composable
fun Test5() {
    val context = LocalContext.current
    val text = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
//    val scrollState = rememberScrollState()
    remember {
        coroutineScope.launch {
            readFile(context, "第一章：刹车时代"){
                text.value = it
            }
        }
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text.value,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(10.dp)

        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun readFile(
    context: Context,
    chapterName: String,
    onRead: (String) -> Unit,
) {
    GlobalScope.launch(Dispatchers.IO) {
        val chapterPath = "$chapterName.txt"
        val inputStream = context.assets.open(chapterPath)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = bufferedReader.readLine()
        var allText = ""
        while (line != null) {
            allText = allText + "\n" + line
            line = bufferedReader.readLine()
        }
        onRead(allText)
    }
}

fun readNextChapter(

){

}


@Preview(showBackground = true)
@Composable
fun Test51() {
    Test5()
}