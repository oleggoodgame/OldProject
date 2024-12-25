package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userLogin: EditText = findViewById(R.id.NameText)


        val userPass: EditText = findViewById(R.id.PasswordText)

        val button: Button = findViewById(R.id.button_reg)

        val textMain: TextView = findViewById(R.id.text_register)

        button.setOnClickListener {

            val login = userLogin.text.toString().trim()

            val pass = userPass.text.toString().trim()

            if(login =="") {

                Toast.makeText(this, "Заповніть Ім'я", Toast.LENGTH_LONG).show()
            }
            else if(pass=="") {

                Toast.makeText(this, "Заповніть пароль", Toast.LENGTH_LONG).show()
            }
            else {
                val db = DbHelper(this, null)
                val result = db.getUser(login, pass)

                when {
                    !result.userExists -> {
                        Toast.makeText(this, "Користувача не знайдено", Toast.LENGTH_LONG).show()
                    }
                    !result.isPasswordCorrect -> {
                        Toast.makeText(this, "Неправильний пароль", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this, "Так, ви є в базі даних", Toast.LENGTH_LONG).show()
                        userLogin.text.clear()
                        userPass.text.clear()
                    }
                }
            }
        }
        textMain.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
