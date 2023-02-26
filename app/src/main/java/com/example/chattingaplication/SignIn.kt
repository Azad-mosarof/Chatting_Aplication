package com.example.chattingaplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIn : AppCompatActivity() {

    private  lateinit var editMail: EditText
    private  lateinit var editPassword: EditText
    private  lateinit var name: EditText
    private  lateinit var signIn: Button
    private  lateinit var auth: FirebaseAuth
    private  lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.hide()

        name = findViewById(R.id.name)
        editMail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        signIn = findViewById(R.id.signIn)
        auth = FirebaseAuth.getInstance()

        signIn.setOnClickListener{
            val name = name.text.toString()
            val email = editMail.text.toString()
            val password = editPassword.text.toString()
            signing(name,email,password)
        }
    }

    private fun signing(name:String,email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,auth.currentUser?.uid!!)
                    val intent = Intent(this@SignIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignIn,"Some Error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String, email:String, uid:String){
        dbRef = FirebaseDatabase.getInstance().reference
        dbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}
