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

    fun getAllDataWithIds(): List<Pair<Int, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Data", null)
        val dataList = mutableListOf<Pair<Int, String>>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                dataList.add(Pair(id, content))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dataList
    }

    fun updateData(id: Int, newContent: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("content", newContent)
        val result = db.update("Data", values, "id=?", arrayOf(id.toString()))
        return result > 0
    }

    fun deleteData(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("Data", "id=?", arrayOf(id.toString()))
        return result > 0
    }
}