package com.example.fragmentapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class InputFragment : Fragment() {
    private var dataPassListener: OnDataPass? = null

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
    ): View {
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        val editText = view.findViewById<EditText>(R.id.editText)
        val button = view.findViewById<Button>(R.id.btnOk)

        button.setOnClickListener {
            val text = editText.text.toString()
            dataPassListener?.onDataPass(text)
        }

        return view
    }
}
