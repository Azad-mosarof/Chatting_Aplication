package com.example.chattingaplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecycleView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var auth:FirebaseAuth
    private lateinit var dbRef:DatabaseReference
    private lateinit var addUser:ArrayList<AddUser>
    private lateinit var temEmailList:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbRef = FirebaseDatabase.getInstance().reference
        userRecycleView = findViewById(R.id.userView)
        auth = FirebaseAuth.getInstance()
        userList = ArrayList()
        addUser = ArrayList()
        temEmailList = ArrayList()
        userAdapter = UserAdapter(this,userList)

        userRecycleView.layoutManager = LinearLayoutManager(this)
        userRecycleView.adapter = userAdapter

        dbRef.child("user").child(auth.currentUser?.uid!!)
            .child("userContacts").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(shot in snapshot.children){
                        val mail = shot.getValue(AddUser::class.java)?.email
                        temEmailList.add(mail!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        dbRef.child("user").addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(currentUser?.email in temEmailList){
                        userList.add(currentUser!!)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logOut){
            auth.signOut()
            val intent = Intent(this@MainActivity,LogIn::class.java)
            finish()
            startActivity(intent)
            return true
        }
        else if(item.itemId == R.id.addUser){
            val intent = Intent(this@MainActivity,AddContact::class.java)
            finish()
            startActivity(intent)
        }
        return  true
    }
}

