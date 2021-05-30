package com.acash.fitmate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.fragments.CommunitiesFragment
import com.acash.fitmate.models.Community
import kotlinx.android.synthetic.main.list_item_community.view.*

class CommunitiesViewHolder(itemView: View, private val fragmentRef: Fragment) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(community: Community) {
        itemView.apply {
            image.setImageResource(community.id)
            tvCommunity.text = community.caption

            if(fragmentRef is CommunitiesFragment) {
                cardCommunity.setOnClickListener {
                    if (cardCommunity.alpha == 0.2F) {
                        cardCommunity.alpha = 1F
                        (fragmentRef as CommunitiesFragment).selectedCommunities.add(tvCommunity.text.toString())
                    } else {
                        cardCommunity.alpha = 0.2F
                        (fragmentRef as CommunitiesFragment).selectedCommunities.remove(tvCommunity.text.toString())
                    }
                }
            }else cardCommunity.alpha = 1F
        }
    }
}