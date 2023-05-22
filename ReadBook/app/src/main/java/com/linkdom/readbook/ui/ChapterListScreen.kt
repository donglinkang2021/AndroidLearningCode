package com.linkdom.readbook.ui

import android.content.ClipData.Item
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.Book
import com.linkdom.readbook.model.Chapter
import com.linkdom.readbook.ui.chapter.ChapterItem

@Composable
fun ChapterListScreen(
    book: Book,
    onTextChange: (String) -> Unit,
    onChapterScreenExpanded: (Boolean) -> Unit
) {
    LazyColumn {
        itemsIndexed(book.chapters) { index, chapter ->
            if (index == 0) {
                BookIntroduction(book = book)
            }
            ChapterItem(chapter = chapter, onTextChanged = {
                onTextChange(it)
            }){
                onChapterScreenExpanded(it)
            }
            if (index == book.chapters.size - 1 && book.chapters.size > 9) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)) {
                    Text(
                        text = "End",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}

@Composable
fun BookIntroduction(book: Book){
    Row (
        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
    ){
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
        ) {
            AsyncImage(
                model = book.cover,
                contentDescription = "Cover of ${book.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Column {
            Text(
                text = book.name,
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "Description: \n ${book.description}",
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.body2
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BookIntroductionPreview() {
    BookIntroduction(book = SampleData.booksSample[6])
}

@Preview(showBackground = true)
@Composable
fun ChapterListScreenPreview() {
    ChapterListScreen(
        book = SampleData.booksSample[6],
        onTextChange = {},
        onChapterScreenExpanded = {}
    )
}