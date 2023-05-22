package com.linkdom.readbook.ui.bookcategory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.BookCategory
import com.linkdom.readbook.ui.book.BookItem

@Composable
fun CategoryItem(bookCategory: BookCategory) {
    var isSeeAll by remember { mutableStateOf(false) }
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = bookCategory.name, style = MaterialTheme.typography.h5)
            OutlinedButton(
                onClick = { isSeeAll = !isSeeAll },
                content = { Text(text = "See All") },
                modifier = Modifier.align(alignment = Alignment.TopEnd)
            )
        }
        Row {
            LazyRow() {
                items(bookCategory.books) { book ->
                    BookItem(book)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun TestCategoryItem(){
    val testCategory= SampleData.bookCategories[0]
    CategoryItem(bookCategory = testCategory)
}