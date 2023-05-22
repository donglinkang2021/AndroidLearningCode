package com.example.simplecomputer.UI

import android.widget.Button
import com.example.simplecomputer.MainActivity

class ClearButton(
    buttonClear: Button,
    mainActivity: MainActivity
) {
    init {
        buttonClear.setOnClickListener {
            mainActivity.showtext.text = ""
        }
    }
}
