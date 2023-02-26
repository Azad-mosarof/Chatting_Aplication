package com.example.chattingaplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth


class messageAdapter(val context:Context, val msgList:ArrayList<Message>) : Adapter<RecyclerView.ViewHolder>() {

    val itemRecive = 1
    val itemSent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return  recieverViewHolder(view)
        }
        else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewHoldar(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val textMsg = msgList[position]
        if(holder.javaClass == sentViewHoldar::class.java){
            val viewHolder = holder as sentViewHoldar
            holder.sentMsg.text = textMsg.message
        }
        else{
            val viewHolder = holder as recieverViewHolder
            holder.recieveMsg.text = textMsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentView = msgList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentView.senderId)){
            return  itemSent
        }
        else{
            return itemRecive
        }
    }

    override fun getItemCount(): Int {
        return  msgList.size
    }

    class sentViewHoldar(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMsg = itemView.findViewById<TextView>(R.id.sent_msg)
    }

    class recieverViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val recieveMsg = itemView.findViewById<TextView>(R.id.recieve_msg)
    }
}