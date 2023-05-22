package com.example.simplecomputer.utils

import android.widget.Toast
import java.util.*
import kotlin.math.pow

class Calculator: JudgeFormula, CalculatorAlgorithm {

    // 定义一个historyList用来存储历史记录
    val history = mutableListOf<String>()
    // 定义一个history_index用来记录当前显示的历史记录的索引
    var historyIndex = 0

    override fun judgeFormula(formula: String): Int {
        // 1. 判断是否为空.
        if (formula.isEmpty()) {
            return 1
        }
        // 2. 判断是否只有一个数字，判断formula中的每一个字符是否都在0-9之间或者是小数点
        var flag = true
        for (element in formula) {
            if (element !in "0123456789.") {
                flag = false
                break
            }
        }
        if (flag) {
            return 3
        }
        // 3. 判断是否只有一个运算符
        if (formula.length == 1 && formula[0] in "+-×÷%") {
            return 1
        }
        // 4. 判断是否以运算符开头
        if (formula[0] in "×÷%") {
            return 1
        }
        // 5. 判断是否以运算符结尾
        if (formula[formula.length - 1] in "+-×÷%") {
            return 1
        }
        // 6. 判断是否有连续的运算符
        for (i in 0 until formula.length - 1) {
            if (formula[i] in "+-×÷%" && formula[i + 1] in "+-×÷%") {
                return 1
            }
        }
        // 8. 判断括号是否匹配
        var left = 0
        var right = 0
        for (i in formula.indices) {
            if (formula[i] == '(') {
                left++
            }
            if (formula[i] == ')') {
                right++
            }
        }
        if (left != right) {
            return 1
        }

        return 0
    }

    override fun compute(formula: String): Double {
        // 用正则表达式找出所有的小数，可能有小数点，也可能没有小数点，但是小数点后面必须有数字，读出的数字是为正数
        val regex = Regex("""(\d+\.?\d*)""")
        // 计算上述formula中的等值
        val stack_operand = Stack<Double>()
        val stack_operator = Stack<Char>()
        stack_operand.push(0.0)
        var i = 0
        while (i < formula.length) {
            val c = formula[i]
            when (c) {
                in '0'..'9', '.' -> {
                    val matchResult = regex.find(formula, i)
                    if (matchResult != null) {
                        val value = matchResult.value.toDouble()
                        stack_operand.push(value)
                        i += matchResult.value.length
                    }
                }

                '+','-', '×', '÷', '%' ,'^' -> {
                    while (stack_operator.isNotEmpty() && priority(stack_operator.peek()) >= priority(c)) {
                        val operator = stack_operator.pop()
                        val operand2 = stack_operand.pop()
                        val operand1 = stack_operand.pop()
                        val value = calculate(operand1, operand2, operator)
                        stack_operand.push(value)
                    }
                    stack_operator.push(c)
                    i++
                }

                '(' -> {
                    stack_operator.push(c)
                    if (formula[i + 1] == '-') {
                        stack_operand.push(0.0)
                        stack_operator.push('-')
                        i++
                    }
                    i++
                }

                ')' -> {
                    while (stack_operator.peek() != '(') {
                        val operator = stack_operator.pop()
                        val operand2 = stack_operand.pop()
                        val operand1 = stack_operand.pop()
                        val value = calculate(operand1, operand2, operator)
                        stack_operand.push(value)
                    }
                    stack_operator.pop()
                    i++
                }

                ' ' -> i++
                else -> throw IllegalArgumentException("Invalid character: $c")
            }
        }
        while (stack_operator.isNotEmpty()) {
            val operator = stack_operator.pop()
            val operand2 = stack_operand.pop()
            val operand1 = stack_operand.pop()
            val value = calculate(operand1, operand2, operator)
            stack_operand.push(value)
        }
        while (stack_operand.size > 1) {
            val operand2 = stack_operand.pop()
            val operand1 = stack_operand.pop()
            val value = calculate(operand1, operand2, '+')
            stack_operand.push(value)
        }
        return stack_operand.pop()
    }

    override fun calculate(operand1: Double, operand2: Double, operator: Char): Double {
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '×' -> operand1 * operand2
            '÷' -> if (operand2 == 0.0) throw IllegalArgumentException("除数不能为0") else operand1 / operand2
            '%' -> operand1 % operand2
            '^' -> operand1.pow(operand2)
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }

    override fun priority(operator: Char?): Int {
        return when (operator) {
            '+', '-' -> 1
            '×', '÷', '%' -> 2
            '^' -> 3
            else -> 0
        }
    }

    fun judgeAndCompute(formula: String) {
        when (judgeFormula(formula)) {
            0 -> {
                try {
                    val result: Double = compute(formula)
                    println(result)
                } catch (e: IllegalArgumentException) {
                        println(e.message)
                }
            }
            1 -> {
                    println("输入的公式不合法")
            }
            3 -> {
                    println("请输入公式")
            }
        }
    }

}