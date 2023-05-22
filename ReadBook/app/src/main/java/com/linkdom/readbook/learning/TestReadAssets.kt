package com.linkdom.readbook.learning

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
fun Test() {
    val context = LocalContext.current
    val text = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
//    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        Button(onClick = {
            coroutineScope.launch {
                readFile(context){
                    text.value = text.value + "\n" + it
                }
            }
        }) {
            Text("读取文件")
        }
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
private fun readFile(
    context: Context,
    onRead: (String) -> Unit
) {
    GlobalScope.launch(Dispatchers.IO) {
//        println("${Thread.currentThread().name}执行文件读取任务。\n")
        val inputStream = context.assets.open("第一章：刹车时代.txt")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            onRead(line)
            line = bufferedReader.readLine()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test4() {
    Test()
}