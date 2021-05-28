package com.acash.fitmate.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acash.fitmate.R
import com.acash.fitmate.models.Challenge
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_challenge.*

class ChallengeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val challengeJsonString = arguments?.getString("ChallengeJsonString").toString()
        val gson = Gson()
        val challenge = gson.fromJson(challengeJsonString, Challenge::class.java)

        challengeImage.setImageResource(challenge.id)
        challengeName.text = challenge.name
        timeLimit.text = challenge.time.toString()

        val countDownTimer = object : CountDownTimer(challenge.time * 60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                lateinit var timeRemaining:String
                val mins = millisUntilFinished/60000
                val seconds = (millisUntilFinished % 60000)/1000
                timeRemaining = "$mins : $seconds"
                tvCountdown?.text = getString(R.string.time_remaining,timeRemaining)
            }

            override fun onFinish() {
                parentFragmentManager.beginTransaction().remove(this@ChallengeFragment).commitAllowingStateLoss()
            }
        }

        countDownTimer.start()

        tasksBtn.setOnClickListener {
            val challengeDialogFragment = ChallengeDialog()
            val bundle = Bundle()

            bundle.putString("ChallengeJsonString",challengeJsonString)

            challengeDialogFragment.arguments = bundle
            challengeDialogFragment.show(parentFragmentManager, null)
        }

        finishBtn.setOnClickListener {
            countDownTimer.cancel()
            parentFragmentManager.beginTransaction().remove(this@ChallengeFragment).commitAllowingStateLoss()
        }
    }

}