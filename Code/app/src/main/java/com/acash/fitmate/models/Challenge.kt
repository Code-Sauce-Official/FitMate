package com.acash.fitmate.models

import com.acash.fitmate.R

class Challenge(
    val id:Int,
    val name:String,
    val time:Long,
    val tasks:ArrayList<String>
){
    companion object{
        fun getChallenges():ArrayList<Challenge> = arrayListOf(
            Challenge(R.drawable.beginner_challenge,"Beginners Challenge", 60,arrayListOf("20 mins walking","10 pushups","10 crunches")),
            Challenge(R.drawable.intermediate_challenge,"Medium Challenge",60,arrayListOf("10 mins jogging","15 pushups","15 crunches")),
            Challenge(R.drawable.advanced_challenge,"Advanced Challenge",60,arrayListOf("18 mins jogging", "25 pushups","25 crunches")),
            Challenge(R.drawable.extreme_challenge,"Extreme Challenge", 60,arrayListOf("20 mins running", "30 pushups","35 crunches")),
            Challenge(R.drawable.athletics_challenge,"Athletics Challenge",40, arrayListOf("15 mins walking","10 mins jogging","10 mins running")),
            Challenge(R.drawable.gymming_challenge,"Workout Challenge",60,arrayListOf("50 pushups","40 crunches", "50 squats")),
            Challenge(R.drawable.cycling_challenge,"Cycling Challenge",40,arrayListOf("20 mins slow cycling","15 mins fast cycling")),
            Challenge(R.drawable.sports_challenge,"Sports Challenge",40,arrayListOf("Play any 2 sports for atleast 20 mins each"))
        )
    }

}