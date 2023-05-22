package com.example.simplecomputer.test

import com.example.simplecomputer.utils.Calculator

fun main(){
    val cal = Calculator()
    val operatorList = listOf('+','-','×','÷','^','%')
    val formula = "(8×9)^2"
    cal.judgeAndCompute(formula)
}