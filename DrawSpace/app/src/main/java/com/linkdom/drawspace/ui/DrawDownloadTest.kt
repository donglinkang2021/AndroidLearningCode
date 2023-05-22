package com.linkdom.drawspace.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.drawspace.data.DigitalInkProvider
import com.linkdom.drawspace.data.DigitalInkProviderImpl


@Composable
fun TestDownLoad(
    digitalInkProvider: DigitalInkProvider,
){
    var isModelDownloaded by remember {
        mutableStateOf(false)
    }
    Row {
        Text(
            modifier = Modifier
                .height(32.dp)
                .weight(1f),
            text = "模型状态：${if (isModelDownloaded) "已下载" else "未下载"}",
        )
        Button(
            modifier = Modifier
                .height(32.dp)
                .weight(1f),
            onClick = {
                // 点击按钮时下载模型
                digitalInkProvider.downloadModel()
            }
        ) {
            Text(text = "下载模型")
        }
        Button(
            modifier = Modifier
                .height(32.dp)
                .weight(1f),
            onClick = {
                isModelDownloaded = digitalInkProvider.checkModelAvailability()
            }
        ) {
            Text(text = "查看模型")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DownloadScreenTest(){
    val digitalInkProvider = DigitalInkProviderImpl()
    TestDownLoad(digitalInkProvider = digitalInkProvider)
}