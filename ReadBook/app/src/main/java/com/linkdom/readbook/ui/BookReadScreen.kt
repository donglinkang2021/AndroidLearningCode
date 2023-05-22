package com.linkdom.readbook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linkdom.readbook.BookReadTopBar
import com.linkdom.readbook.data.SampleData

@Composable
fun BookReadScreen(bookID: String){
    val book = SampleData.booksSample[bookID.toInt()]
    var isChapterListExpanded by remember{ mutableStateOf(true)}
    var text by remember { mutableStateOf("请选择章节") }
    Scaffold(
        topBar = {
            BookReadTopBar(
                book = book,
                onChapterListExpandedChange = { isChapterListExpanded = !isChapterListExpanded }
            )
        },
        content = if (isChapterListExpanded) {
                {
                    ChapterListScreen(
                        book = book,
                        onTextChange = {
                            text = it
                        },
                        onChapterScreenExpanded = {
                        isChapterListExpanded = !it
                    })
                }
            } else {
                {
                    ContentScreen(text = text)
                }
            }
    )
}

@Composable
fun ContentScreen(text : String){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = text,
        )
        Box (
            modifier = Modifier.fillMaxWidth(),
                ){
            OutlinedButton(
                onClick = {

                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(text = "上一章")
            }
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(text = "下一章")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestContentScreen(){
    ContentScreen(text = "测试")
}


@Preview(showBackground = true)
@Composable
fun TestBookReadScreen(){
    BookReadScreen(bookID = "6")
}
