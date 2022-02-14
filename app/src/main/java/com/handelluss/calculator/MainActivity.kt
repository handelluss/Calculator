package com.handelluss.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.Exception
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private val buttons : ArrayList<ArrayList<Button>> = arrayListOf()
    private lateinit var field : TextView

    private var storedOperand : Double? = null
    private var storedOperator : Char? = null

    private var needsToClear : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        field = findViewById(R.id.inputText)
        initializeButtons()
        initializeListeners()
    }

    private fun initializeButtons () {
        buttons.add(arrayListOf(
            findViewById(R.id.buttonAllClear),
            findViewById(R.id.buttonPlusMinus),
            findViewById(R.id.buttonPercent),
            findViewById(R.id.buttonDivision)
        ))
        buttons.add(arrayListOf(
            findViewById(R.id.buttonSeven),
            findViewById(R.id.buttonEight),
            findViewById(R.id.buttonNine),
            findViewById(R.id.buttonMultiple)
        ))
        buttons.add(arrayListOf(
            findViewById(R.id.buttonFour),
            findViewById(R.id.buttonFive),
            findViewById(R.id.buttonSix),
            findViewById(R.id.buttonMinus)
        ))
        buttons.add(arrayListOf(
            findViewById(R.id.buttonOne),
            findViewById(R.id.buttonTwo),
            findViewById(R.id.buttonThree),
            findViewById(R.id.buttonPlus)
        ))
        buttons.add(arrayListOf(
            findViewById(R.id.buttonZero),
            findViewById(R.id.buttonComma),
            findViewById(R.id.buttonCalculate)
        ))
    }

    private fun initializeListeners() {
        for (list in buttons) {
            for (button in list) {
                button.setOnClickListener {
                    when (button.text) {
                        "=" -> equalsAction()
                        "AC" -> allClearAction()
                        "+/-" -> plusMinusAction()
                        "+", "-", "*", "/", "%" -> operatorAction(button.text[0])
                        "," -> numberAction('.')
                        else -> numberAction(button.text[0])
                    }
                }
            }
        }
    }

    // Action Methods **************************

    private fun equalsAction () {
        if (field.text.isNotEmpty() && storedOperand != null) {
            calculate()
            storedOperand = null
            storedOperator = null
        }
    }

    private fun allClearAction () {
        field.text = ""
        storedOperand = null
        storedOperator = null
    }

    private fun plusMinusAction () {
        if (field.text.isEmpty() || field.text[0] != '-'){
            field.text = "-" + field.text
        }
        else {
            field.text = field.text.subSequence(1, field.text.length)
        }
    }

    private fun operatorAction(clickedOperator : Char) {
        if (storedOperand != null && storedOperator != null) {
            val result = calculate()
            storedOperand = result
            storedOperator = clickedOperator
        }
        else if (storedOperand == null && field.text.isNotEmpty()) {
            storedOperand = field.text.toString().toDouble()
            storedOperator = clickedOperator
            needsToClear = true
        }
    }

    private fun numberAction(clickedNumber : Char) {
        if (needsToClear) clearField()
        field.text = field.text.toString() + clickedNumber
    }

    // Help Methods **************************

    private fun clearField () {
        field.text = ""
        needsToClear = false
    }

    private fun calculate() : Double {
        val secondOperand = field.text.toString().toDouble()
        val result : Double = when(storedOperator) {
            '+' -> storedOperand!! + secondOperand
            '-' -> storedOperand!! - secondOperand
            '*' -> storedOperand!! * secondOperand
            '/' -> storedOperand!! / secondOperand
            '%' -> storedOperand!! * (secondOperand / 100.0)
            else -> throw Exception("storedOperator has wrong value")
        }
        setDoubleText(result)
        needsToClear = true

        return result
    }

    private fun setDoubleText (numberToSet : Double) {
        if (floor(numberToSet) == numberToSet) // if Double value is mathematical integer
            field.text = numberToSet.toInt().toString()
        else
            field.text = numberToSet.toString()
    }
}

