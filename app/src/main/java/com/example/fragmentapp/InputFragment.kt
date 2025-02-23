package com.example.fragmentapp

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class InputFragment : Fragment() {
    private var dataPassListener: OnDataPass? = null
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioShow: RadioButton
    private lateinit var radioHide: RadioButton
    private lateinit var editText: EditText

    interface OnDataPass {
        fun onDataPass(data: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDataPass) {
            dataPassListener = context
        } else {
            throw RuntimeException("$context must implement OnDataPass")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        // Ініціалізація елементів інтерфейсу
        editText = view.findViewById(R.id.editText)
        val button = view.findViewById<Button>(R.id.btnOk)
        radioGroup = view.findViewById(R.id.radioGroup)
        radioShow = view.findViewById(R.id.radioShow)
        radioHide = view.findViewById(R.id.radioHide)

        // Логіка зміни режиму відображення паролю
        radioShow.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }
        }

        radioHide.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        // Логіка кнопки "ОК"
        button.setOnClickListener {
            val text = editText.text.toString()
            dataPassListener?.onDataPass(text)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        // Очистити форму при переході на інший фрагмент
        editText.text.clear()
        radioGroup.clearCheck()
    }
}

