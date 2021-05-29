package com.acash.fitmate.fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.acash.fitmate.R
import com.acash.fitmate.models.Challenge
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_challenge.*

const val MILLIS_REMAINING = "Milliseconds Remaining"
const val TIME_WHEN_DESTROYED = "Time when Destroyed"
const val CHALLENGE_INDEX = "Challenge Index"

class ChallengeFragment : Fragment() {

    private lateinit var countDownTimer:CountDownTimer
    private var millisRemaining = 0L
    private lateinit var challenge:Challenge
    var challengeFinished = false

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
        challenge = gson.fromJson(challengeJsonString, Challenge::class.java)

        challengeImage.setImageResource(challenge.id)
        challengeName.text = challenge.name
        timeLimit.text = challenge.time.toString()

        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        var millisinFuture = prefs.getLong(MILLIS_REMAINING,0L)
        val timeWhenDestroyed = prefs.getLong(TIME_WHEN_DESTROYED,-1L)

        if(millisinFuture==0L){
            millisinFuture = challenge.time * 60000
        }else{
            millisinFuture-=(System.currentTimeMillis()-timeWhenDestroyed)
        }

        countDownTimer = object : CountDownTimer(millisinFuture, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                lateinit var timeRemaining:String
                val mins = millisUntilFinished/60000
                val seconds = (millisUntilFinished % 60000)/1000
                timeRemaining = "$mins : $seconds"
                tvCountdown?.text = getString(R.string.time_remaining,timeRemaining)
                millisRemaining = millisUntilFinished
            }

            override fun onFinish() {
                challengeFinished = true
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
            challengeFinished = true
            parentFragmentManager.beginTransaction().remove(this@ChallengeFragment).commitAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()

        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)

        if(challengeFinished){
            prefs.edit {
                putLong(MILLIS_REMAINING,0L)
                putLong(TIME_WHEN_DESTROYED,-1L)
                putInt(CHALLENGE_INDEX,-1)
            }
        }else prefs.edit {
            putLong(MILLIS_REMAINING,millisRemaining)
            putLong(TIME_WHEN_DESTROYED,System.currentTimeMillis())
            putInt(CHALLENGE_INDEX,challenge.index)
        }
    }

}