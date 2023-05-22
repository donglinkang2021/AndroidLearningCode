package com.linkdom.readbook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.BookCategory
import com.linkdom.readbook.ui.bookcategory.CategoryItem2
import com.linkdom.readbook.ui.theme.ReadBookTheme

@Composable
fun ReadBookApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        BookShelter(bookCategorys = SampleData.bookCategories)
    }
}


@Composable
fun BookShelter(bookCategorys : List<BookCategory> ){
    Surface(
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(){
            items(bookCategorys) { bookCategory ->
                CategoryItem2(bookCategory = bookCategory)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestReadBookAppPreview() {
    ReadBookTheme {
        ReadBookApp()
    }
}



