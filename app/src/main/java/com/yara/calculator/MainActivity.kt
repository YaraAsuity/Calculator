package com.yara.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.yara.calcolater.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var input = ""
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)

        binding.btnFraction.setOnClickListener {
            handleFraction(binding.btnFraction)
        }

        // Delete button
        binding.btnDelete.setOnClickListener {
            if (binding.txtIn.text.isNotEmpty()) {
                input = binding.txtIn.text.toString().dropLast(1)
                binding.txtIn.text = input
            }
        }

        binding.btnDelete.setOnLongClickListener {
            if (binding.txtIn.text.isNotEmpty()) {
                input = ""
                binding.txtIn.text = ""
                binding.txtOut.text = ""
            }
            true
        }

        // Operator buttons
        binding.btnPlus.setOnClickListener { operatorOnScreen(binding.btnPlus) }
        binding.btnMinus.setOnClickListener { operatorOnScreen(binding.btnMinus) }
        binding.btnMult.setOnClickListener { operatorOnScreen(binding.btnMult) }
        binding.btnDiv.setOnClickListener { operatorOnScreen(binding.btnDiv) }

        // Equal button
        binding.btnEqual.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        var result: Double

        when {
            input.contains("-") -> {
                val calc = input.split("-")
                result = calc[0].toDouble() - calc[1].toDouble()
            }
            input.contains("*") -> {
                val calc = input.split("*")
                result = calc[0].toDouble() * calc[1].toDouble()
            }
            input.contains("+") -> {
                val calc = input.split("+")
                result = calc[0].toDouble() + calc[1].toDouble()
            }
            input.contains("/") -> {
                val calc = input.split("/")
                val denominator = calc[1].toDouble()
                if (denominator == 0.0) {
                    Toast.makeText(this, "Can't divide by zero", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    result = calc[0].toDouble() / denominator
                }
            }
            else -> return
        }

        binding.txtOut.text = deleteDecimal(result.toString())
        input = deleteDecimal(result.toString())
    }

    private fun deleteDecimal(num: String): String {
        val n = num.split(".")
        return if (n.size > 1 && n[1] == "0") {
            n[0]
        } else {
            num
        }
    }

    private fun operatorOnScreen(btn: MaterialButton) {
        val num = binding.txtIn.text.toString()
        if (num.isNotEmpty()) {
            val ch = num.last()
            if (ch != '+' && ch != '-' && ch != '*' && ch != '/') {
                input += btn.text.toString()
                binding.txtIn.text = input
            }
        }
    }

    private fun handleFraction(btn: MaterialButton) {
        val num = binding.txtIn.text.toString()

        if (num.isNotEmpty()) {
            val ch = num.last()
            if (ch != '.') {
                input += btn.text.toString()
                binding.txtIn.text = input
            }
        } else {
            input += btn.text.toString()
            binding.txtIn.text = "0$input"
        }
    }

    override fun onClick(view: View) {
        val btn = view as MaterialButton
        input += btn.text.toString()
        binding.txtIn.text = input
    }


}
