package com.acash.fitmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acash.fitmate.R
import com.acash.fitmate.adapters.SlideScreenAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_partners.*

class MyPartnersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_partners, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = SlideScreenAdapter(requireActivity())
        TabLayoutMediator(tabs,viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Partners"
                }

                1 -> {
                    tab.text = "Requests"
                }
            }
        }.attach()
    }

}