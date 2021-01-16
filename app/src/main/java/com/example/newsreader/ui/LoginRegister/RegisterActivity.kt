package com.example.newsreader.ui.LoginRegister

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsreader.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class RegisterActivity : AppCompatActivity() {
    lateinit var loginButton: Button
    lateinit var signUpButton: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var repeatPasswordEditText: EditText
    lateinit var nickEditText: EditText
    lateinit var mAuth :FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginButton = findViewById(R.id.buttonRegActLogin)
        loginButton.setOnClickListener(
            View.OnClickListener {
                super.onBackPressed()
            }
        )
        signUpButton = findViewById(R.id.buttonRegActRegister)
        emailEditText = findViewById(R.id.editTextActRegEmail)
        passwordEditText = findViewById(R.id.editTextActRegPassword)
        repeatPasswordEditText = findViewById(R.id.editTextActRegPassword2)
        nickEditText = findViewById(R.id.editTextRegActNick)
        mAuth = FirebaseAuth.getInstance()




        signUpButton.setOnClickListener(
            View.OnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val repeatPassword = repeatPasswordEditText.text.toString()
                val nick = nickEditText.text.toString()
                val credentialsOK: String = checkCredentials(email, password, repeatPassword, nick)


                var allDataIsGood = true

                if (credentialsOK != "") {
                    allDataIsGood = false
                    Toast.makeText(this,credentialsOK,Toast.LENGTH_SHORT).show()
                    return@OnClickListener

                }

                val timestamp = Timestamp.now().toDate()
                val timestampPlus3months = Date(timestamp.year, timestamp.month + 3, timestamp.day, 0, 0, 0)
                val userDataMap = hashMapOf(
                    "nick" to nick,
                    "subscription" to timestampPlus3months
                )

                val db = FirebaseFirestore.getInstance()
                db.collection("Users")
                    .whereEqualTo("nick", nick)
                    .get().addOnSuccessListener { documents ->
                        run {
                            if (!documents.isEmpty) {

                                Toast.makeText(this, "Nick already exists", Toast.LENGTH_SHORT)
                                    .show()
                            } else {

                                //mAuth.createUserWithEmailAndPassword(email, password)
                                //val user = mAuth.currentUser
                                //if (userUID == null) {
                                //Toast.makeText(this, "Failed to connect to database", Toast.LENGTH_SHORT).show()
                                // return@addOnSuccessListener
                                //}
                                //db.collection("Users").document(userUID).set(userDataMap)
                                //val userDoc = db.collection("Users").document(userUID)
                                //.addOnSuccessListener { Toast.makeText(this, "user data added", Toast.LENGTH_SHORT).show() }
                                //.addOnFailureListener { Toast.makeText(this, "failed to add user data", Toast.LENGTH_SHORT).show() }




                                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                                    val user = mAuth.currentUser
                                    val userUID = user?.uid
                                    if (userUID == null) {
                                        Toast.makeText(this, "Failed to connect to database", Toast.LENGTH_SHORT).show()
                                        return@addOnSuccessListener
                                    }
                                    val nickDataMap = hashMapOf(
                                        "id" to userUID
                                    )

                                    db.collection("Users").document(userUID).set(userDataMap)
                                        .addOnSuccessListener { Toast.makeText(this, "user data added", Toast.LENGTH_SHORT).show() }
                                        .addOnFailureListener{Toast.makeText(this, "failed to add user data", Toast.LENGTH_SHORT).show() }
                                }

                                    .addOnFailureListener {
                                        Toast.makeText(this, "failed to create user", Toast.LENGTH_SHORT).show()
                                    }

                            }



                        }

                    }.addOnFailureListener{Toast.makeText(this, "Failed to connect to database", Toast.LENGTH_SHORT).show()}



            }


        )


    }


    /*
    private fun checkIfNickDoesntExist(nick :String) :Boolean
    {

        val db = FirebaseFirestore.getInstance()
        var nickDoesntExist = true
        var connectionGood = true

        db.collection("Users")
                .whereEqualTo("nick",  nick)
                .get().addOnSuccessListener {
                    //nickDoesntExist=nieOgarniamCoTuSieOdjaniepawla(it)
                    documents ->
                    run {
                        if (!documents.isEmpty) {
                            nickDoesntExist = false

                            Toast.makeText(this, "Nick already exists $nickDoesntExist", Toast.LENGTH_SHORT)
                                    .show()
                            val matchingDocs = documents.documents
                            for (d: DocumentSnapshot in matchingDocs) {
                                val nickFromDoc = d["nick"]
                                val docName = d.id
                                Toast.makeText(this, "nick in doc: $nickFromDoc, doc: $docName", Toast.LENGTH_SHORT).show()
                            }

                        }
                        Toast.makeText(this, "nick doesnt exist : $nickDoesntExist", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener { exception ->
                    run {
                        //nickDoesntExistOrConnectionFail = lambdaExprFun()
                        connectionGood = false
                        Toast.makeText(
                                this,
                                "Error while connecting to database",
                                Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(this, "connection good : $connectionGood", Toast.LENGTH_SHORT).show()
                    }
                }


        return nickDoesntExist && connectionGood
    }

    private fun nieOgarniamCoTuSieOdjaniepawla(docs :QuerySnapshot) :Boolean
    {

        if(!docs.isEmpty) {
            Toast.makeText(this, "Nick already exists", Toast.LENGTH_SHORT)
                    .show()
            return false
        }
        return true
    }

    private fun addDataAboutUserToFirestore(user: FirebaseUser, nick: String) {
        val db = FirebaseFirestore.getInstance()
        val userUID = user.uid
        val timestamp = Timestamp.now().toDate()
        val timestampPlus3months = Date( timestamp.year, timestamp.month + 3, timestamp.day, 0 ,0 ,0)
        val userDataMap = hashMapOf(
            "nick" to nick,
            "subscription" to timestampPlus3months
        )
        val nickDataMap = hashMapOf(
                "id" to userUID
                )
        db.collection("Users").document(userUID).set(userDataMap)
            .addOnSuccessListener { Toast.makeText(this, "user data added", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener{ Toast.makeText(this, "failed to add user data", Toast.LENGTH_SHORT).show()}
        db.collection("Nicks").document(nick).set(nickDataMap)
                .addOnSuccessListener { Toast.makeText(this, "user data added", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener{ Toast.makeText(this, "failed to add user data", Toast.LENGTH_SHORT).show()}
    }
    */

    private fun checkCredentials(email: String, password: String, repeatPassword: String, nick :String): String {


        if(email.isEmpty())
            return "Enter Email"

        if(password.isEmpty() || repeatPassword.isEmpty())
            return  "Enter password"

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "incorrect email"

        if(password !=repeatPassword)
        {
            return "Passwords not matching"
        }
        if(!passwordStrong(password))
        {
            return "Password must contain min. 8 characters including upper and lower case letter, digit and special character"
        }
        if(nick.isEmpty())
        {
            return "Enter nick"
        }

        return ""
    }


    private fun passwordStrong(password: String): Boolean {
        if(containsOneOfChars(password, "qwertyuiopasdfghjklzxcvbnm") && containsOneOfChars(
                password,
                "QWERTYUIOPASDFGHJKLZXCVBNM"
            ) &&
            containsOneOfChars(password, "1234567890") && containsOneOfChars(
                password,
                "!@#$%^&*()"
            ) && password.length>=8)
            return true

        return false
    }

    private fun containsOneOfChars(password: String, chars: String): Boolean {
        for (c:Char in chars)
        {
            if(password.contains(c))
                return true
        }
        return false
    }
}