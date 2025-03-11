package com.example.fragmentapp

import android.content.Context
import android.content.Intent
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
    private lateinit var databaseHelper: DatabaseHelper

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
        databaseHelper = DatabaseHelper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        editText = view.findViewById(R.id.editText)
        val buttonOk = view.findViewById<Button>(R.id.btnSave)
        val buttonOpen = view.findViewById<Button>(R.id.btnOpen) //layout/ fragment_input does not contain a declaration with id btnOpen Toggle info (Ctrl+F1)
        radioGroup = view.findViewById(R.id.radioGroup)
        radioShow = view.findViewById(R.id.radioShow)
        radioHide = view.findViewById(R.id.radioHide)

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

        buttonOk.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                val isInserted = databaseHelper.insertData(text)
                if (isInserted) {
                    Toast.makeText(context, "Data is successfully saved!", Toast.LENGTH_SHORT).show()
                    dataPassListener?.onDataPass(text)
                } else {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Field can't be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonOpen.setOnClickListener {
            val intent = Intent(context, DataDisplayActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        editText.text.clear()
        radioGroup.clearCheck()
    }
}
