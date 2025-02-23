package com.example.fragmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val textView = view.findViewById<TextView>(R.id.textView)
        val buttonCancel = view.findViewById<Button>(R.id.btnCancel)

        textView.text = arguments?.getString("data")

        buttonCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        fun newInstance(data: String) = ResultFragment().apply {
            arguments = Bundle().apply {
                putString("data", data)
            }
        }
    }
}
