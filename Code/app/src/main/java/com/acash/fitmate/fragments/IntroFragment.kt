package com.acash.fitmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acash.fitmate.R
import com.acash.fitmate.activities.AuthActivity
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        proceedBtn.setOnClickListener {
            (activity as AuthActivity).setFragment(EmailFragment())
        }
    }
}