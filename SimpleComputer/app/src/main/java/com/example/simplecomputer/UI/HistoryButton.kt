package com.example.simplecomputer.UI

import android.widget.Button
import com.example.simplecomputer.utils.Calculator
import com.example.simplecomputer.MainActivity

class HistoryButton(
    buttonHistoryLast: Button,
    buttonHistoryNext: Button,
    cal: Calculator,
    mainActivity: MainActivity
) {
    init {
        buttonHistoryLast.setOnClickListener {
            if (cal.history.size > 0) {
                if (cal.historyIndex == 0) {
                    cal.historyIndex = 0
                } else {
                    cal.historyIndex -= 1
                }
                mainActivity.showtext.text = cal.history[cal.historyIndex]
            }
        }

        buttonHistoryNext.setOnClickListener {
            if (cal.history.size > 0) {
                if (cal.historyIndex == cal.history.size - 1) {
                    cal.historyIndex = cal.history.size - 1
                } else {
                    cal.historyIndex += 1
                }
                mainActivity.showtext.text = cal.history[cal.historyIndex]
            }
        }
    }

}
