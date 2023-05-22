package com.linkdom.readbook.learning

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.linkdom.readbook.data.SampleData

@Composable
fun MyScreen() {
    // 是否展开章节列表
    var isChapterListExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Screen") },
                navigationIcon = {
                    IconButton(
                        onClick = { isChapterListExpanded = true },
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
            )
        },
        content = (if (isChapterListExpanded) {
            {
                ChapterListScreen(
                    onChapterSelected = { /* 处理章节选中事件 */ },
                    onBackClick = { isChapterListExpanded = false },
                )
            }
        } else {
            {
                // 主要内容
                // ...
            }
        })
    )

}

@Composable
fun ChapterListScreen(onChapterSelected: (Int) -> Unit, onBackClick: () -> Unit) {
    // 实现章节列表的 UI
    com.linkdom.readbook.ui.ChapterListScreen(
        book = SampleData.booksSample[0],
        onTextChange = { /* 处理章节内容改变事件 */ },
        onChapterScreenExpanded = {
        onChapterSelected(0)
    })

    // 如果用户点击后退按钮，则收起 Swipeable 组件
    IconButton(
        onClick = onBackClick,
    ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    MyScreen()
}
