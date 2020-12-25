package com.example.newsreader.LoginRegister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.newsreader.R
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    lateinit var regButton: Button
    lateinit var mAuth : FirebaseAuth
    lateinit var loginButton: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var userID :String
    lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        regButton = findViewById(R.id.buttonLogActRegister)
        regButton.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        )
        loginButton = findViewById(R.id.buttonLogActLogin)
        emailEditText = findViewById(R.id.editTextLogActEmail)
        passwordEditText = findViewById(R.id.editTextLogActPassword)
        backButton = findViewById((R.id.buttonLogActBack))
        mAuth = FirebaseAuth.getInstance()

        backButton.setOnClickListener(View.OnClickListener { this.onBackPressed() })

        loginButton.setOnClickListener(
            View.OnClickListener {
                val email: String = emailEditText.text.toString()
                val password: String = passwordEditText.text.toString()
                val credentialsOK = checkCredentials(email, password)
                if (credentialsOK != "") {

                    Toast.makeText(this, "$credentialsOK", Toast.LENGTH_SHORT)
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            this
                        ) { task ->
                            if (task.isSuccessful) {
                                //val user = mAuth.currentUser
                                //userID = user.providerId
                                Toast.makeText(
                                    this, "signed in",
                                    Toast.LENGTH_SHORT
                                ).show()
                                super.onBackPressed()

                            } else {
                                Toast.makeText(
                                    this, "Incorrect login or password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            // ...
                        }
                }
            }
        )





    }

    private fun checkCredentials(email: String, password: String): String {
        if(email.isEmpty())
            return "Enter email"
        if(password.isEmpty())
            return "Enter password"

        return ""
    }
}