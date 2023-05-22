package com.linkdom.readbook.ui.bookcategory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.BookCategory
import com.linkdom.readbook.ui.book.BookItem2

@Composable
fun CategoryItem2(bookCategory: BookCategory) {
    var isExpanded by remember { mutableStateOf(false) }
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = bookCategory.name, style = MaterialTheme.typography.h5)
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.rotate(if (isExpanded) 90f else 0f)
                )
            }
        }

        Card(
            elevation = 3.dp,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            if (!isExpanded || bookCategory.books.size < 3)
            {
                LazyRow() {
                    items(bookCategory.books) { book ->
                        BookItem2(book)
                    }
                }
            }else{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(410.dp)
                ){
                    items(bookCategory.books.size){ item ->
                        BookItem2(bookCategory.books[item])
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun TestCategoryItem2(){
    val testCategory= SampleData.bookCategories[1]
    CategoryItem2(bookCategory = testCategory)
}