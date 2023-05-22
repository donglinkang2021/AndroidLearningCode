package com.linkdom.readbook.ui.book

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linkdom.readbook.BookReadActivity
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.Book


@Composable
fun BookItem2(book: Book) {
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


    Column {
        Card(
            elevation = 6.dp,
            modifier = Modifier
                .padding(4.dp)
                .border(
                    1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(4.dp)
                )
                .height(150.dp)
                .width(100.dp)
                .clickable {
                    val intent = Intent(context, BookReadActivity::class.java)
                    intent.putExtra("bookID", book.id)
                    launcher.launch(intent)
                },
        ) {
            AsyncImage(
                model = book.cover,
                contentDescription = "Cover of ${book.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Text(text = book.name, modifier = Modifier
            .padding(4.dp)
            .width(100.dp), maxLines = 2)
    }
}

@Preview(showBackground = true)
@Composable
fun TestBookItem2(){
//    val testBook = SampleData.myBooks[0]
    val testBook = SampleData.booksSample[0]
    BookItem2(book = testBook)
}
