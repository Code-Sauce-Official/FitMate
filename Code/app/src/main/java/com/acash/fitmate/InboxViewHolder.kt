package com.acash.fitmate

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.models.Inbox
import com.acash.fitmate.utils.formatAsListItem
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.list_item_partner.view.*

class InboxViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(inbox: Inbox, onClick:(name:String, uid:String, thumbImg:String)->Unit) = with(itemView){
        countTv.isVisible = inbox.count>0
        countTv.text = inbox.count.toString()
        timeTv.text = inbox.time.formatAsListItem(context)

        titleTv.text = inbox.name
        subtitleTv.text = inbox.msg

        if(inbox.image!="")
            Glide.with(itemView).load(inbox.image).placeholder(R.drawable.defaultavatar).error(R.drawable.defaultavatar).into(userImgView)
        else
            Glide.with(itemView).load(R.drawable.defaultavatar).into(userImgView)

        val database = FirebaseDatabase.getInstance("https://fitmate-f33d2-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database.reference.child("user_status/${inbox.from}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val online = snapshot.value
                if (online == inbox.from) {
                    userStatus.visibility = View.VISIBLE
                }
                else {
                    userStatus.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(itemView.context, "Error Occur", Toast.LENGTH_SHORT).show()
            }
        })

        setOnClickListener{
            onClick.invoke(inbox.name,inbox.from,inbox.image)
        }
    }
}