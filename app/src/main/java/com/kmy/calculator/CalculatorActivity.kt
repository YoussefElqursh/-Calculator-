package com.kmy.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.button.MaterialButton
import org.mozilla.javascript.Context

class CalculatorActivity : ComponentActivity() , View.OnClickListener{

    private lateinit var solutionTv: TextView
    private lateinit var resultTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_calculator)

        solutionTv = findViewById(R.id.solution_tv)
        resultTv = findViewById(R.id.result_tv)

        assignId(R.id.btn_clear)
        assignId(R.id.btn_clear_all)
        assignId(R.id.btn_open_brackets)
        assignId(R.id.btn_close_brackets)
        assignId(R.id.btn_dot)
        assignId(R.id.btn_division)
        assignId(R.id.btn_multiple)
        assignId(R.id.btn_addition)
        assignId(R.id.btn_subtraction)
        assignId(R.id.btn_equals)
        assignId(R.id.btn_0)
        assignId(R.id.btn_1)
        assignId(R.id.btn_2)
        assignId(R.id.btn_3)
        assignId(R.id.btn_4)
        assignId(R.id.btn_5)
        assignId(R.id.btn_6)
        assignId(R.id.btn_7)
        assignId(R.id.btn_8)
        assignId(R.id.btn_9)
    }

    private fun assignId(id: Int) {
        val btn = findViewById<MaterialButton>(id)
        btn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
            val button = view as MaterialButton
            val btnName = button.text.toString()
            var equation = solutionTv.text.toString()

            when (btnName) {
                "AC" -> {
                    solutionTv.text = ""
                    resultTv.text = "0"
                }
                "=" -> {
                    solutionTv.text = resultTv.text
                }
                "C" -> {
                    if (equation.isNotEmpty()) {
                        equation = equation.substring(0, equation.length - 1)
                    } else {
                        solutionTv.text = ""
                        resultTv.text = "0"
                    }
                }
                else -> {
                    equation += btnName
                }
            }

        solutionTv.text = equation

        val result = getResult(equation)
        if (result != "error") {
            resultTv.text = result
        }
    }

    private fun getResult(data: String): String {
        return try {
            val context = Context.enter()
            context.optimizationLevel = -1
            val scope = context.initSafeStandardObjects()
            var result = context.evaluateString(scope, data, "JavaScript", 1, null).toString()
            if (result.endsWith(".0")) {
                result = result.replace(".0", "")
            }
            result
        } catch (e: Exception) {
            "error"
        } finally {
            Context.exit() // Important to exit the context
        }
    }
}
