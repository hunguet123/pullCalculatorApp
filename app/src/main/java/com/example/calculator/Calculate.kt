package com.example.calculator

import java.util.ArrayDeque

class Calculate {
    private var inputList : MutableList<Any> = mutableListOf()

    fun digitsOperator(txtInput : String) {
        var currentDigit = ""
        for (character in txtInput) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else  {
                inputList.add(currentDigit.toFloat())
                inputList.add(character)
                currentDigit = ""
            }
        }
        if (currentDigit != "") {
            inputList.add(currentDigit.toFloat())
        }
    }

    fun clearList() {
        inputList.clear()
    }

    fun getResult() : Float {
        return evaluatePostfix()
    }


    // kiem tra do lon cua dau cong tru nhan chia
    private fun getPriority(operator : Any) : Int {
        if (operator !is Char) return -2
        when (operator) {
            '+', '-' -> return 0
            '×', '÷' -> return 1
        }
        return -1
    }

    //tinh gia tri postfix
    private fun evaluatePostfix() : Float {
        val postFix = convertInfixToPostfix()
        val stack = ArrayDeque<Any>()
            for (i in postFix) {
                if (i is Float) {
                    stack.push(i)
                } else {
                    val x = stack.pop() as Float
                    var y = stack.pop() as Float
                    when (i) {
                        '+' -> y += x
                        '-' -> y -= x
                        '×' -> y *= x
                        '÷' -> y /= x
                    }
                    stack.push(y)
                }
            }
        return stack.pop() as Float
    }

    private fun convertInfixToPostfix() : MutableList<Any> {
        val postFix : MutableList<Any> = mutableListOf()
        val stack = ArrayDeque<Any>()
        for (i in inputList) {
            when (i) {
                is Float -> {
                    postFix.add(i)
                }
                '(' -> {
                    stack.push(i)
                }
                ')' -> {
                    while (!stack.isEmpty() && stack.peek() != "(" ) {
                        postFix.add(stack.pop())
                    }
                    if (!stack.isEmpty()) {
                        stack.pop()
                    }
                }
                else -> {
                    while (!stack.isEmpty() && getPriority(i as Char) <= getPriority(stack.peek() as Char)) {
                        postFix.add(stack.pop())
                    }
                    stack.push(i)
                    if (stack.size == 1) continue
                }
            }
        }
        while (!stack.isEmpty()) {
            postFix.add(stack.pop())
        }
        return postFix
    }

}