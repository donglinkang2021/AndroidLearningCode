package com.example.simplecomputer.utils

interface CalculatorAlgorithm{
    fun compute(formula: String): Double
    fun calculate(operand1: Double, operand2: Double, operator: Char): Double
    fun priority(operator: Char?): Int
}