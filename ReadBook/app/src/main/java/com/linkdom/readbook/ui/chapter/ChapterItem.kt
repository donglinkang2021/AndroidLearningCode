package com.linkdom.readbook.ui.chapter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.readbook.data.SampleData
import com.linkdom.readbook.fileio.readFile.readFile
import com.linkdom.readbook.makeToast
import com.linkdom.readbook.model.Chapter
import kotlinx.coroutines.launch


@Composable
fun ChapterItem(
    chapter: Chapter,
    onTextChanged: (String) -> Unit,
    onChapterClicked: (Boolean) -> Unit
) {
    val content = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .padding(4.dp)
            .clickable(
                onClick = {
//                    makeToast(content, chapter.name + " clicked")
                    onChapterClicked(true)
                    coroutineScope.launch {
                        readFile(content, chapter.name) {
                            onTextChanged(it)
                        }
                    }
                },
            )
            .fillMaxWidth()
            .then(Modifier.height(50.dp)),
    ) {
        Text(
            text = chapter.name,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .wrapContentSize(align = Alignment.CenterStart),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChapterItemPreview() {
    var isExpanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    Column {
        ChapterItem(
            chapter = SampleData.TheWanderingEarthChapter[0],
            onTextChanged = {
                text = it
            }
        ) {
            isExpanded = it
        }
        if (isExpanded) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            )
        }
    }
}
