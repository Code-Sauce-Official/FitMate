package com.acash.fitmate.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.R
import com.acash.fitmate.fragments.ChallengeDialog
import com.acash.fitmate.models.Challenge
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_challenge.view.*

class ChallengesAdapter(private val listChallenges:ArrayList<Challenge>,private val fragmentRef: Fragment) : RecyclerView.Adapter<ChallengesAdapter.ChallengesViewholder>() {

    class ChallengesViewholder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengesViewholder = ChallengesViewholder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_challenge,parent,false)
    )

    override fun onBindViewHolder(holder: ChallengesViewholder, position: Int) {
        holder.itemView.apply {
            challengeName.text = listChallenges[position].name
            challengeImage.setImageResource(listChallenges[position].id)
            challengeCard.setOnClickListener {
                val challengeDialogFragment = ChallengeDialog()
                val bundle = Bundle()

                val gson = Gson()
                val challengeJsonString = gson.toJson(listChallenges[position])
                bundle.putString("ChallengeJsonString",challengeJsonString)

                challengeDialogFragment.arguments = bundle
                challengeDialogFragment.show(fragmentRef.childFragmentManager, null)
            }
        }
    }

    override fun getItemCount() = listChallenges.size
}

