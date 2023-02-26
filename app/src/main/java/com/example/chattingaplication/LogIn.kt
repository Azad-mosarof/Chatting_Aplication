package com.example.chattingaplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private  lateinit var editMail: EditText
    private  lateinit var editPassword: EditText
    private  lateinit var login: Button
    private  lateinit var signIn: Button
    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        editMail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        login = findViewById(R.id.login)
        signIn = findViewById(R.id.signIn)

        signIn.setOnClickListener {
            val intent = Intent(this,SignIn::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val email = editMail.text.toString()
            val password = editPassword.text.toString()
            login(email,password)
        }
    }

    private  fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn,"User Does Not Exit",Toast.LENGTH_SHORT).show()
                }
            }
    }
}