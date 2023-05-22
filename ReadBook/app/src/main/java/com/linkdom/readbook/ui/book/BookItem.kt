package com.linkdom.readbook.ui.book

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.readbook.BookReadActivity
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.Book


@Composable
fun BookItem(book: Book) {
    val context = LocalContext.current
    val info = remember {
        mutableStateOf("")
    }

    // 注册启动Activity的launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            info.value = data?.getStringExtra("info") ?: ""
        }else{
            info.value = "No data"
        }
    }

    var expanded by remember { mutableStateOf(false) }
    val extraCardHeight by animateDpAsState(
        targetValue = if (expanded) 400.dp else 150.dp,
        animationSpec = tween(
            durationMillis = 300,
        )
    )
    val extraCardWidth by animateDpAsState(
        targetValue = if (expanded) 300.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 300,
        )
    )
    Card(
        elevation = 6.dp,
        modifier = Modifier
            .padding(4.dp)
            .border(
                1.dp,
                color = androidx.compose.ui.graphics.Color.Black,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .height(extraCardHeight)
            .width(extraCardWidth)
    ) {

        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable(onClick = { expanded = !expanded })
        ) {

            Column (
                modifier = Modifier.align(Alignment.TopStart)
                    ){
                Text(
                    text = book.name,
                    style = MaterialTheme.typography.h6,
                )
                if (expanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Description: \n\n ${book.description}",
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Box (
                modifier = Modifier.align(Alignment.BottomEnd)
                    ){
                if (expanded) {

                    IconButton(onClick = {
                        /* Open New Activity Here */
                        val intent = Intent(context, BookReadActivity::class.java)
                        intent.putExtra("info", "Hello from MainActivity")
                        launcher.launch(intent)
                    }) {
                        Text(text = "Read")
                    }
                }else{
                    Text(
                        text = book.author,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestBookItem(){
    val testBook = SampleData.booksSample[6]
    BookItem(book = testBook)
}
