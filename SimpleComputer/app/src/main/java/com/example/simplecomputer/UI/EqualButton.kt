package com.example.simplecomputer.UI

import android.widget.Button
import android.widget.Toast
import com.example.simplecomputer.utils.Calculator
import com.example.simplecomputer.MainActivity

class EqualButton (
    button: Button,
    val cal: Calculator,
    val mainActivity: MainActivity
){
    init
    {
        button.setOnClickListener {
            val formula = mainActivity.showtext.text.toString()
            // 将输入的表达式加入历史记录
            cal.history.add(formula)
            cal.historyIndex = cal.history.size - 1
            // 用定义judgeFormula函数判断输入的表达式是否合法
            when (cal.judgeFormula(formula)) {
                0 -> {
                    try {
                        val result: Double = cal.compute(formula)
//                        println(result)
                        mainActivity.showtext.text = result.toString()
                    } catch (e: IllegalArgumentException) {
//                        println(e.message)
                        Toast.makeText(mainActivity, "除数不能为0", Toast.LENGTH_SHORT).show()
                    }
                }
                1 -> {
//                    println("输入的公式不合法")
                    Toast.makeText(mainActivity, "输入的公式不合法", Toast.LENGTH_SHORT).show()
                }
                3 -> {
//                    println("请输入公式")
                    Toast.makeText(mainActivity, "请输入公式", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}