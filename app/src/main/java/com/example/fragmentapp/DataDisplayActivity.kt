package com.example.fragmentapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DataDisplayActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private var selectedItemId: Int? = null
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dataList: MutableList<Pair<Int, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_display)

        databaseHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewData)
        emptyTextView = findViewById(R.id.emptyTextView)
        editButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)

        loadData()

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedItemId = dataList[position].first
            editButton.visibility = Button.VISIBLE
            deleteButton.visibility = Button.VISIBLE
        }

        editButton.setOnClickListener {
            selectedItemId?.let { id ->
                showEditDialog(id)
            }
        }

        deleteButton.setOnClickListener {
            selectedItemId?.let { id ->
                deleteData(id)
            }
        }

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }

    private fun loadData() {
        val rawData = databaseHelper.getAllDataWithIds()
        dataList = rawData.toMutableList()
        val displayList = dataList.map { it.second }

        if (dataList.isEmpty()) {
            emptyTextView.text = "Storage is empty"
            emptyTextView.visibility = TextView.VISIBLE
            listView.visibility = ListView.GONE
            editButton.visibility = Button.GONE
            deleteButton.visibility = Button.GONE
        } else {
            emptyTextView.visibility = TextView.GONE
            listView.visibility = ListView.VISIBLE
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
            listView.adapter = adapter
        }
    }

    private fun showEditDialog(id: Int) {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Data")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newData = editText.text.toString()
                if (newData.isNotEmpty()) {
                    databaseHelper.updateData(id, newData)
                    loadData()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun deleteData(id: Int) {
        databaseHelper.deleteData(id)
        selectedItemId = null
        editButton.visibility = Button.GONE
        deleteButton.visibility = Button.GONE
        loadData()
    }
}