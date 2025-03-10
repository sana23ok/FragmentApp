package com.example.fragmentapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DataDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_display)

        val textView = findViewById<TextView>(R.id.textViewData)
        val databaseHelper = DatabaseHelper(this)
        val dataList = databaseHelper.getAllData()

        textView.text = if (dataList.isNotEmpty()) {
            dataList.joinToString("\n")
        } else {
            "No data"
        }
    }
}
