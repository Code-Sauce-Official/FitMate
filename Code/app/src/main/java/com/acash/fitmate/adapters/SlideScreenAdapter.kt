package com.acash.fitmate.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.acash.fitmate.fragments.PartnersFragment
import com.acash.fitmate.fragments.RequestsFragment

class SlideScreenAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount():Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> PartnersFragment()
        else -> RequestsFragment()
    }

}