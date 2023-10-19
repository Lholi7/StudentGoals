package com.example.studentgoals

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "goals.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "goals"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_DESCRIPTION = "description"
        private const val COL_IS_COMPLETED = "is_completed"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT,
                $COL_DESCRIPTION TEXT,
                $COL_IS_COMPLETED INTEGER DEFAULT 0
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertGoal(title: String, description: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, title)
        contentValues.put(COL_DESCRIPTION, description)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllGoals(): List<Goal> {
        val goalList = mutableListOf<Goal>()
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        } catch (e: SQLException) {
            db.execSQL("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_TITLE TEXT,$COL_DESCRIPTION TEXT,$COL_IS_COMPLETED INTEGER DEFAULT 0);")
            return ArrayList()
        }

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(COL_ID)
                val titleIndex = cursor.getColumnIndex(COL_TITLE)
                val descriptionIndex = cursor.getColumnIndex(COL_DESCRIPTION)
                val isCompletedIndex = cursor.getColumnIndex(COL_IS_COMPLETED)

                val id = cursor.getInt(idIndex)
                val title = cursor.getString(titleIndex)
                val description = cursor.getString(descriptionIndex)
                val isCompleted = cursor.getInt(isCompletedIndex) == 1

                val goal = Goal(id, title, description, isCompleted)
                goalList.add(goal)
            }
            cursor.close()
        }
        return goalList
    }

    fun getGoalByTitle(title: String): Goal? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_TITLE=?", arrayOf(title))
        return if (cursor != null && cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COL_ID)
            val titleIndex = cursor.getColumnIndex(COL_TITLE)
            val descriptionIndex = cursor.getColumnIndex(COL_DESCRIPTION)
            val isCompletedIndex = cursor.getColumnIndex(COL_IS_COMPLETED)

            val id = cursor.getInt(idIndex)
            val title = cursor.getString(titleIndex)
            val description = cursor.getString(descriptionIndex)
            val isCompleted = cursor.getInt(isCompletedIndex) == 1

            val goal = Goal(id, title, description, isCompleted)
            cursor.close()
            goal
        } else {
            null
        }
    }


    fun updateGoalCompletion(title: String, isCompleted: Boolean) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_IS_COMPLETED, if (isCompleted) 1 else 0)
        db.update(TABLE_NAME, values, "$COL_TITLE=?", arrayOf(title))
    }

    fun deleteGoal(title: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_TITLE=?", arrayOf(title))
    }
}

