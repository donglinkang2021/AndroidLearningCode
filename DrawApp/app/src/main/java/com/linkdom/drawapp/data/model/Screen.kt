package com.linkdom.drawapp.data.model

import com.linkdom.drawapp.ui.Destinations

data class Screen(
    val destinations: Destinations,
    val previewImage: String = "",
    val title: String,
    val description: String,
    val tags: List<String> = emptyList()
)