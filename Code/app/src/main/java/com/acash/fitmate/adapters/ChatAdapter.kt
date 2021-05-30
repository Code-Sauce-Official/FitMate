package com.acash.fitmate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.R
import com.acash.fitmate.models.ChatEvent
import com.acash.fitmate.models.DateHeader
import com.acash.fitmate.models.Messages
import com.acash.fitmate.utils.formatAsTime
import kotlinx.android.synthetic.main.list_item_chat_sent_msg.view.*
import kotlinx.android.synthetic.main.list_item_date_header.view.*

class ChatAdapter(private val list:MutableList<ChatEvent>, private val currentUid:String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = {layout:Int->
            LayoutInflater.from(parent.context).inflate(layout,parent,false)
        }

        return when(viewType){
            TEXT_MESSAGE_RECEIVED-> MessageViewHolder(inflate(R.layout.list_item_chat_receive_msg))
            TEXT_MESSAGE_SENT-> MessageViewHolder(inflate(R.layout.list_item_chat_sent_msg))
            else->DateViewHolder(inflate(R.layout.list_item_date_header))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item=list[position]){
            is DateHeader ->{
                holder.itemView.textView.text= item.date
            }

            is Messages ->{
                holder.itemView.apply {
                    tvMsg.text = item.msg
                    tvTime.text = item.sentAt.formatAsTime()
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when(val event = list[position]){
            is Messages ->{
                if(event.senderId==currentUid){
                    TEXT_MESSAGE_SENT
                }else TEXT_MESSAGE_RECEIVED
            }
            is DateHeader -> DATE_HEADER
            else-> UNSUPPORTED
        }
    }

    class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object{
        private const val UNSUPPORTED = -1
        private const val TEXT_MESSAGE_RECEIVED = 0
        private const val TEXT_MESSAGE_SENT = 1
        private const val DATE_HEADER = 2
    }
}

