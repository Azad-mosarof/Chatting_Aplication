package com.example.chattingaplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private var i:Int = 0

class AddContact : AppCompatActivity() {

    private lateinit var contactsEmail:EditText
    private lateinit var addContact:Button
    private lateinit var dbRef:DatabaseReference
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        contactsEmail = findViewById(R.id.contactasEmail)
        addContact = findViewById(R.id.addContacts)
//        var flag:Int = 0
        dbRef = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        addContact.setOnClickListener{

            dbRef.child("user").child(auth.currentUser?.uid!!).child("userContacts")
                .child("$i").setValue(AddUser(contactsEmail.text.toString()))
            i+=1
            val intent = Intent(this@AddContact,MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}


//if(flag==1){
//    dbRef.child("user").child(auth.currentUser?.uid!!).child("userContacts")
//        .child("$i").setValue(AddUser(contactsEmail.text.toString()))
//    i+=1
//    val intent = Intent(this@AddContact,MainActivity::class.java)
//    finish()
//    startActivity(intent)
//}
//else{
//    Toast.makeText(this@AddContact,"Sorry user does not have account on LESO",Toast.LENGTH_SHORT).show()
//}

//dbRef.child("user").addValueEventListener(object : ValueEventListener {
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onDataChange(snapshot: DataSnapshot) {
//
//        for(postSnapshot in snapshot.children){
//            val currentUser = postSnapshot.getValue(User::class.java)
//            if(currentUser?.email == contactsEmail.text.toString()){
//                flag=1
//                break
//            }
//        }
//        if(flag==0){
//            Toast.makeText(this,"Sorry user does not have account on LESO",sho)
//        }
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//
//    }
//})