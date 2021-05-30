package com.acash.fitmate.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.acash.fitmate.R
import com.acash.fitmate.models.Challenge
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_challenge_dialog.*

class ChallengeDialog : DialogFragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val challengeJsonString = arguments?.getString("ChallengeJsonString").toString()
        val gson = Gson()
        val challenge = gson.fromJson(challengeJsonString, Challenge::class.java)

        challengeImage.setImageResource(challenge.id)
        challengeName.text = challenge.name
        timeLimit.text = "${challenge.time} mins"

        if (parentFragmentManager.fragments.size == 2) {
            btnTakeChallenge.visibility = View.GONE
        }

        challengeDescription.text = "→ "
        challengeDescription.append(TextUtils.join("\n→ ", challenge.tasks))

        dismissBtn.setOnClickListener {
            dismiss()
        }

        btnTakeChallenge.setOnClickListener {
            val fragment = ChallengeFragment()
            val bundle = Bundle()
            bundle.putString("ChallengeJsonString", challengeJsonString)
            fragment.arguments = bundle
            dismiss()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

    }

}