package com.acash.fitmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.CommunitiesViewHolder
import com.acash.fitmate.R
import com.acash.fitmate.models.Community

class CommunitiesAdapter(private val listCommunities:ArrayList<Community>,private val fragmentRef: Fragment) : RecyclerView.Adapter<CommunitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesViewHolder = CommunitiesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_community, parent, false),
            fragmentRef
        )

    override fun onBindViewHolder(holder: CommunitiesViewHolder, position: Int) {
        holder.bind(listCommunities[position])
    }

    override fun getItemCount(): Int = listCommunities.size

}