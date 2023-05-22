package com.linkdom.readbook.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {

    val chapterIndex = mutableStateOf(0)
    val position = mutableStateOf(0)

    init {
        // Initialize variables
        chapterIndex.value = 0
        position.value = 0
    }

    fun onChapterSelected(index: Int) {
        chapterIndex.value = index
        position.value = 0
    }

    fun onPositionSelected(pos: Int) {
        position.value = pos
    }

    fun getCurrentChapterAndPosition(): Pair<Int, Int> {
        return Pair(chapterIndex.value, position.value)
    }
}
