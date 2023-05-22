package com.linkdom.readbook

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.model.Book

@Composable
fun BookReadTopBar(
    book: Book,
    onChapterListExpandedChange: () -> Unit
) {
    var isShowMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = book.name) },
        navigationIcon = {
            IconButton(
                onClick = onChapterListExpandedChange,
            ) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {
                makeToast(context, "收藏")
            }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }

            IconButton(
                onClick = { isShowMenu = !isShowMenu },
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = isShowMenu, onDismissRequest = { isShowMenu = false }) {
                DropdownMenuItem(onClick = {
                    makeToast(context, "添加书签")
                }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_bookmark_24), "")
                    Text(text = "添加书签")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookReadTopBarPreview() {
    BookReadTopBar(book = SampleData.booksSample[6], onChapterListExpandedChange = {})
}