package com.linkdom.learningcompose2

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun TopicsList(topics: List<Topic>) {
    LazyColumn {
        topics.forEach() { topic ->
            item {
                TopicChip(topic = topic, selected = false)
            }
        }
    }
}