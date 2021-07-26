package com.acash.fitmate

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.fragments.UserInfoFragment
import com.acash.fitmate.models.Form
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_match.view.*
import java.util.*

class MatchViewHolder(itemView: View, private val activityRef: Activity):RecyclerView.ViewHolder(itemView) {
    fun bind(form: Form){
        itemView.apply {
            tvName.text = form.name
            tvGender.text = form.gender
            tvAge.text = (Calendar.getInstance().get(Calendar.YEAR)-form.yearOfBirth).toString()

            matchCard.setOnClickListener {

                val fragment = UserInfoFragment()
                val bundle = Bundle()

                val gson = Gson()
                val formJsonString = gson.toJson(form)
                bundle.putString("FormJsonString",formJsonString)

                fragment.arguments = bundle

                (activityRef as MainActivity).setFragment(fragment)

            }
        }
    }
}