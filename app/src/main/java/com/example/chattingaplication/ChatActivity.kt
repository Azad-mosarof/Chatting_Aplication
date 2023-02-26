package com.example.chattingaplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecycleView: RecyclerView
    private lateinit var editMsg:EditText
    private lateinit var sentImg:ImageView
    private lateinit var msgAdapter:messageAdapter
    private lateinit var msgList:ArrayList<Message>
    private lateinit var dbRef:DatabaseReference

    private var senderRoom:String? = null
    private var reciverRoom:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val recieveruid = intent.getStringExtra("uid")

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = recieveruid + senderuid
        reciverRoom = senderuid + recieveruid

        supportActionBar?.title = name

        dbRef = FirebaseDatabase.getInstance().reference
        chatRecycleView = findViewById(R.id.itemView)
        editMsg = findViewById(R.id.editMsg)
        sentImg = findViewById(R.id.sentImg)
        msgList = arrayListOf()
        msgAdapter = messageAdapter(this,msgList)

        chatRecycleView.layoutManager = LinearLayoutManager(this@ChatActivity)
        chatRecycleView.adapter = msgAdapter

        //Logic for adding data in Recycaler View
        dbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    msgList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        msgList.add(message!!)
                    }
                    msgAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        //adding the msg to the database
        sentImg.setOnClickListener{
            val senderMsg = editMsg.text.toString()
            val msgObject = Message(senderMsg,senderuid)
            dbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(msgObject).addOnSuccessListener {
                    dbRef.child("chats").child(reciverRoom!!).child("messages").push()
                        .setValue(msgObject)
                }
            editMsg.setText("")
        }

    }
}