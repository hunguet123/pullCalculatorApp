package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var calculate = Calculate()
    private var inputText = ""
    private var canAddOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickNumber(view: View) {
        if (view is Button) {
            if ((textViewCalculate.text.isEmpty() || textViewCalculate.text.toString().last() == '.') && view.text.toString() == ".")
            {
                canAddOperation = false
            } else {
                textViewCalculate.append(view.text)
                inputText += view.text.toString()
                canAddOperation = textViewCalculate.text.toString().last() != '.'
            }
        }
    }

    fun onClickOperator(view: View) {
        if (view is Button && canAddOperation) {
            textViewCalculate.append(view.text)
            inputText += view.text.toString()
            canAddOperation = false
        }
    }

    fun onClickAllClear(view: View) {
        textViewResult.text = ""
        textViewCalculate.text = ""
        calculate.clearList()
        canAddOperation = false
        inputText = ""
    }

    fun onClickResult(view: View) {
        if (canAddOperation) {
            if (inputText.isNotEmpty()) {
                calculate.digitsOperator(inputText)
                textViewResult.text = calculate.getResult().toString()
                calculate.clearList()
            } else {
                textViewResult.text = ""
            }
        } else {
            textViewResult.text = ""
            Toast.makeText(this, R.string.error_input_number, Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickPercent(view: View) {
        if (view is Button && canAddOperation) {
            textViewCalculate.append(view.text)
            inputText += "÷100"
            canAddOperation = true
        }
    }

    fun onClickDelete(view: View) {
        if (view is Button) {
            if (textViewCalculate.text.isNotEmpty()) {
                val textCalculate = textViewCalculate.text
                var lastElement: Char = textCalculate[textCalculate.length - 1]
                textViewCalculate.text = textCalculate.substring(0, textCalculate.length - 1)
                inputText = if (lastElement == '%') {
                    inputText.substring(0, inputText.length - 4)
                } else {
                    inputText.substring(0, inputText.length - 1)
                }
                if (inputText.isNotEmpty()) {
                    lastElement = inputText[inputText.length - 1]
                } else {
                    calculate.clearList()
                }
                canAddOperation = if (inputText.isEmpty()) {
                    false
                } else {
                    lastElement.isDigit()
                }

            } else {
                canAddOperation = false
            }
        }
    }


}
