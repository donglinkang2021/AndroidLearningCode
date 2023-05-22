package com.example.simplecomputer.UI

import android.widget.Button
import com.example.simplecomputer.MainActivity

class DeleteButton(
    buttonDel: Button,
    mainActivity: MainActivity
) {
    init {
        buttonDel.setOnClickListener {
            mainActivity.showtext.text = mainActivity.showtext.text.toString().dropLast(1)
        }
        // 长按清空
        buttonDel.setOnLongClickListener {
            mainActivity.showtext.text = ""
            true
        }
    }
}
