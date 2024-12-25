package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class UserCheckResult(
    var userExists: Boolean,
    var isPasswordCorrect: Boolean
)

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {
    /*
    *context: Це контекст вашого додатку.
    "app": Це назва вашої бази даних.
    factory: Це null, оскільки ви не використовуєте кастомний CursorFactory.
    1: Це початкова версія вашої бази даних.
    * */
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT NULL, login TEXT, email TEXT, pass TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues().apply {
            put("login", user.name)
            put("email", user.email)
            put("pass", user.password)
        }

        val db = this.writableDatabase // відкриває базу даних для запису, дозволяючи вам здійснювати операції запису, такі як вставка, оновлення або видалення даних. Це важливий крок для роботи з базою даних в Android.
        db.insert("users", null, values)
        db.close()
    }

    fun getUser(login: String, pass: String): UserCheckResult {
        val db = this.readableDatabase

        val userCheckResult = UserCheckResult(false, false)

        val result1 = db.rawQuery("SELECT * FROM users WHERE login = '$login'", null)
        if (result1.moveToFirst()) {
            userCheckResult.userExists = true
            val result2 = db.rawQuery("SELECT * FROM users WHERE pass = '$pass'", null)
            if (result2.moveToFirst()) {
                userCheckResult.isPasswordCorrect = true
            }
            result2.close()
        }
        result1.close()


        return userCheckResult
    }
}
