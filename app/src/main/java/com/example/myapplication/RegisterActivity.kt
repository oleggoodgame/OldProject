package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userLogin: EditText = findViewById(R.id.NameText)

        val userEmail: EditText = findViewById(R.id.EmailText)

        val userPass: EditText = findViewById(R.id.PasswordText)

        val button: Button = findViewById(R.id.button_reg)

        val textMain: TextView = findViewById(R.id.text_main)

        button.setOnClickListener {

            val login = userLogin.text.toString().trim()

            val email = userEmail.text.toString().trim()

            val pass = userPass.text.toString().trim()

            if(login =="") {

                Toast.makeText(this, "Заповніть Ім'я", Toast.LENGTH_LONG).show()
            }
            else if(pass=="") {

                Toast.makeText(this, "Заповніть пароль", Toast.LENGTH_LONG).show()
            }
            else if(email =="") {

                Toast.makeText(this, "Заповніть емейл", Toast.LENGTH_LONG).show()
            }
            else {
                val user = User(login,pass,email)
                val db = DbHelper(this, null)
                db.addUser(user)
                Toast.makeText(this, "Вітаю ви зареєструвались $login", Toast.LENGTH_LONG).show()

                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()
            }

        }
        textMain.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

