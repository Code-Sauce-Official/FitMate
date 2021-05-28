package com.acash.fitmate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.fitmate.R
import com.acash.fitmate.activities.MainActivity
import com.acash.fitmate.adapters.ChallengesAdapter
import com.acash.fitmate.adapters.CommunitiesAdapter
import com.acash.fitmate.models.Challenge
import com.acash.fitmate.models.Community
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    var doesViewExist = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doesViewExist = true

        val challengesAdapter = ChallengesAdapter(Challenge.getChallenges(),this)

        rvChallenges.apply {
            adapter = challengesAdapter
            layoutManager = GridLayoutManager(requireContext(),4,LinearLayoutManager.VERTICAL,false)
        }

        updateDate(Date())

        (activity as MainActivity).currentUserInfo?.let {
            initializeOtherViews()
        }
    }

     fun initializeOtherViews(){

        (activity as MainActivity).currentUserInfo?.let {user->
            val selectedCommunitiesName = user.communities
            val listCommunities = ArrayList<Community>()

            for (communityName in selectedCommunitiesName) {
                Community.getCommunities().filter {
                    it.caption == communityName
                }.forEach {
                    listCommunities.add(it)
                }
            }

            val communitiesAdapter = CommunitiesAdapter(listCommunities, this)

            rvCommunities.apply {
                adapter = communitiesAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

    }

    private fun updateDate(date:Date) {
        val myFormat = "EEEE"
        val sdf = SimpleDateFormat(myFormat)
        tvDay.text = sdf.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        doesViewExist = false
    }
}