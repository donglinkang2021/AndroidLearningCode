package com.example.simplecomputer.UI

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.HorizontalScrollView
import com.example.simplecomputer.MainActivity

@SuppressLint("SetTextI18n")
class AddTextButton (
    button: Button,
    val text: String,
    val mainActivity: MainActivity
) {
    init {
        button.setOnClickListener {
            mainActivity.showtext.text = mainActivity.showtext.text.toString() + text
            mainActivity.scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }
    }

}