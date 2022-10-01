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
    private var canAddOperation : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if ((txt_calculate.text.isEmpty() || txt_calculate.text.toString().last() == '.') && view.text.toString() == ".")
            {
                canAddOperation = false
            } else {
                txt_calculate.append(view.text)
                inputText += view.text.toString()
                canAddOperation = txt_calculate.text.toString().last() != '.'
            }
        }
    }

    fun operatorAction(view: View) {
        if (view is Button && canAddOperation) {
            txt_calculate.append(view.text)
            inputText += view.text.toString()
            canAddOperation = false
        }
    }

    fun allClearAction(view: View) {
        txt_result.text = ""
        txt_calculate.text = ""
        calculate.clearList()
        canAddOperation = false
        inputText = ""
    }

    fun resultAction(view: View) {
        if (canAddOperation) {
            if (inputText.isNotEmpty()) {
                calculate.digitsOperator(inputText)
                txt_result.text = calculate.result().toString()
                calculate.clearList()
            } else {
                txt_result.text = ""
            }
        } else {
            txt_result.text = ""
            Toast.makeText(this, "CÓ LỖI NHẬP SỐ", Toast.LENGTH_SHORT).show()
        }
    }

    fun percentAction(view: View) {
        if (view is Button && canAddOperation) {
            txt_calculate.append(view.text)
            inputText += "÷100"
            canAddOperation = true
        }
    }

    fun deleteAction(view: View) {
        if (view is Button) {
            if (txt_calculate.text.isNotEmpty()) {
                val textCalculate = txt_calculate.text
                var lastElement: Char = textCalculate[textCalculate.length - 1]
                txt_calculate.text = textCalculate.substring(0, textCalculate.length - 1)
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
