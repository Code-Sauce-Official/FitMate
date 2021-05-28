package com.acash.fitmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.CommunitiesViewholder
import com.acash.fitmate.R
import com.acash.fitmate.models.Community

class CommunitiesAdapter(private val listCommunities:ArrayList<Community>,private val fragmentRef: Fragment) : RecyclerView.Adapter<CommunitiesViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesViewholder =
        CommunitiesViewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_community, parent, false),
            fragmentRef
        )

    override fun onBindViewHolder(holder: CommunitiesViewholder, position: Int) {
        holder.bind(listCommunities[position])
    }

    override fun getItemCount(): Int = listCommunities.size
}