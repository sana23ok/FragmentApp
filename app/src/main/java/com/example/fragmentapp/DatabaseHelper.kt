package com.example.fragmentapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "data.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Data (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Data")
        onCreate(db)
    }

    fun insertData(content: String): Boolean {
        val db = writableDatabase
        return try {
            val values = ContentValues()
            values.put("content", content)
            val result = db.insert("Data", null, values)
            result != -1L
        } finally {
            db.close()
        }
    }


    fun getAllDataWithIds(): List<Pair<Int, String>> {
        val db = readableDatabase
        val dataList = mutableListOf<Pair<Int, String>>()

        val cursor = db.query("Data", arrayOf("id", "content"), null, null, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow("id"))
                val content = it.getString(it.getColumnIndexOrThrow("content"))
                dataList.add(id to content)
            }
        }
        db.close()
        return dataList
    }


    fun updateData(id: Int, newContent: String): Boolean {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("content", newContent)
            }
            val result = db.update("Data", values, "id=?", arrayOf(id.toString()))
            result > 0
        } finally {
            db.close()
        }
    }

    fun deleteData(id: Int): Boolean {
        val db = writableDatabase
        return try {
            val result = db.delete("Data", "id=?", arrayOf(id.toString()))
            result > 0
        } finally {
            db.close()
        }
    }

}