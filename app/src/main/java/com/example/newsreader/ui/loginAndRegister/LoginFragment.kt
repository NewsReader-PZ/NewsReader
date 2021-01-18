package com.example.newsreader.ui.loginAndRegister

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsreader.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    lateinit var regButton: Button
    lateinit var mAuth : FirebaseAuth
    lateinit var loginButton: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var userID :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//ddddd

    }

    private fun checkCredentials(email: String, password: String): String {
        if(email.isEmpty())
            return "Enter email"
        if(password.isEmpty())
            return "Enter password"

        return ""
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
                ViewModelProvider(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
           // textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(R.layout.activity_login)
        regButton = view.findViewById(R.id.buttonLogActRegister)
        regButton.setOnClickListener(
                View.OnClickListener {
                    val intent = Intent(context, RegisterActivity::class.java)
                    startActivity(intent)
                }
        )
        loginButton = view.findViewById(R.id.buttonLogActLogin)
        emailEditText = view.findViewById(R.id.editTextLogActEmail)
        passwordEditText = view.findViewById(R.id.editTextLogActPassword)
        mAuth = FirebaseAuth.getInstance()

        //backButton.setOnClickListener(View.OnClickListener { this.onBackPressed() })

        loginButton.setOnClickListener(
                View.OnClickListener {
                    val email: String = emailEditText.text.toString()
                    val password: String = passwordEditText.text.toString()
                    val credentialsOK = checkCredentials(email, password)
                    if (credentialsOK != "") {

                        Toast.makeText(context, "$credentialsOK", Toast.LENGTH_SHORT)
                    } else {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(
                                ) { task ->
                                    if (task.isSuccessful) {
                                        //val user = mAuth.currentUser
                                        //userID = user.providerId

                                        var uid :String?= mAuth.uid
                                        if(uid!=null && uid!="")
                                        {
                                            //loginViewModel.userHasSignedIm(uid)

                                                //replace unction above with corutine function
                                            //attempt to replace observer with corutine
                                                GlobalScope.launch {
                                                    loginViewModel.suspUserHasSignedIn(uid!!)
                                                }
                                            Log.println(Log.INFO,"logging to firebase","User ID: $uid")
                                            //end of changes
                                        }
                                        else
                                        {uid = ""
                                            Toast.makeText(
                                                context, "failed to retrieve user nick",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.println(Log.INFO,"logging to firebase","Signed in - user unknown")
                                        }

                                        Toast.makeText(
                                            context, "signed in",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        //super.onBackPressed()

                                    } else {
                                        Toast.makeText(
                                                context, "Incorrect login or password",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    // ...
                                }
                    }
                }
        )



    }
}