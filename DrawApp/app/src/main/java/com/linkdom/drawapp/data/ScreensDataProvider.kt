package com.linkdom.drawapp.data

import com.linkdom.drawapp.data.model.Screen
import com.linkdom.drawapp.ui.Destinations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScreensDataProviderImpl @Inject constructor(): ScreensDataProvider {

    override fun fetchScreen(): Flow<List<Screen>> = flow {
        emit(listOf(
                Screen(
                    destinations = Destinations.DigitalInk,
                    previewImage = "https://github.com/surajsau/ML-Android/raw/main/screenshots/translate_app.gif",
                    title = "On-device Google Translate",
                    description = "Character Recognition & Translation using Google MLKit",
                    tags = listOf("MLKit")
                ),
            )
        )
    }
}

interface ScreensDataProvider {
    fun fetchScreen(): Flow<List<Screen>>
}
