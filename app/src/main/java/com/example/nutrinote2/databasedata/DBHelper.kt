package com.example.nutrinote2.databasedata

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + EMAIL_COL + " TEXT)")
        db.execSQL(createTableQuery)
    }

    fun addUser(username: String?, password: String?, email: String?) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(USERNAME_COL, username)
        values.put(PASSWORD_COL, password)
        values.put(EMAIL_COL, email)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun findByUsername(username: String?): User? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $USERNAME_COL = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(username))
        val user = if (cursor.moveToFirst()) {
            User(
                username = cursor.getString(cursor.getColumnIndex(USERNAME_COL)),
                email = cursor.getString(cursor.getColumnIndex(EMAIL_COL)),
                password = cursor.getString(cursor.getColumnIndex(PASSWORD_COL))
            )
        } else {
            null
        }
        cursor.close()
        db.close()
        return user
    }

    @SuppressLint("Range")
    fun findByEmail(email: String?): User? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $EMAIL_COL = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(email))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                username = cursor.getString(cursor.getColumnIndex(USERNAME_COL)),
                email = cursor.getString(cursor.getColumnIndex(EMAIL_COL)),
                password = cursor.getString(cursor.getColumnIndex(PASSWORD_COL))
            )
        }
        cursor.close()
        // db.close() -- removed as explained in the previous message
        return user
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "userdb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val ID_COL = "id"
        private const val USERNAME_COL = "username"
        private const val PASSWORD_COL = "password"
        private const val EMAIL_COL = "email"
    }
}
