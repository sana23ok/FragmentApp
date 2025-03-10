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
        val values = ContentValues()
        values.put("content", content)
        val result = db.insert("Data", null, values)
        return result != -1L
    }

    fun getAllData(): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Data", null)
        val dataList = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                dataList.add(content)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dataList
    }
}
