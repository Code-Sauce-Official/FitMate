package com.acash.fitmate.adapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.R
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.fragments.PostInfoFragment
import com.acash.fitmate.models.Form
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_post.view.*
import java.text.SimpleDateFormat

class PostsAdapter(private val posts: ArrayList<Form>, private val activityRef: Activity) :
    RecyclerView.Adapter<PostsAdapter.PostsViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewholder =
        PostsViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        )

    override fun onBindViewHolder(holder: PostsViewholder, position: Int) {
        holder.itemView.apply {
            tvPostNo.text = "Post ${position+1}"
            val myFormat = "d MMM yyyy"
            val sdf = SimpleDateFormat(myFormat)
            posts[position].createdDate?.let {
                tvCreatedDate.text = sdf.format(it)
            }

            postCard.setOnClickListener {

                val fragment = PostInfoFragment()
                val bundle = Bundle()

                val gson = Gson()
                val formJsonString = gson.toJson(posts[position])
                bundle.putString("FormJsonString", formJsonString)
                bundle.putString("PostNo",tvPostNo.text.toString())

                fragment.arguments = bundle

                (activityRef as MainActivity).setFragment(fragment)

            }
        }
    }

    override fun getItemCount() = posts.size

    class PostsViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)

}


