package com.acash.fitmate.models

import com.acash.fitmate.R

class Challenge(
    val index:Int,
    val id:Int,
    val name:String,
    val time:Long,
    val tasks:ArrayList<String>
){
    companion object{
        fun getChallenges():ArrayList<Challenge> = arrayListOf(
            Challenge(0,R.drawable.beginner_challenge,"Beginners Challenge", 60,arrayListOf("20 mins walking","10 pushups","10 crunches")),
            Challenge(1,R.drawable.intermediate_challenge,"Medium Challenge",60,arrayListOf("10 mins jogging","15 pushups","15 crunches")),
            Challenge(2,R.drawable.advanced_challenge,"Advanced Challenge",60,arrayListOf("18 mins jogging", "25 pushups","25 crunches")),
            Challenge(3,R.drawable.extreme_challenge,"Extreme Challenge", 60,arrayListOf("20 mins running", "30 pushups","35 crunches")),
            Challenge(4,R.drawable.athletics_challenge,"Athletics Challenge",40, arrayListOf("15 mins walking","10 mins jogging","10 mins running")),
            Challenge(5,R.drawable.gymming_challenge,"Workout Challenge",60,arrayListOf("50 pushups","40 crunches", "50 squats")),
            Challenge(6,R.drawable.cycling_challenge,"Cycling Challenge",40,arrayListOf("20 mins slow cycling","15 mins fast cycling")),
            Challenge(7,R.drawable.sports_challenge,"Sports Challenge",40,arrayListOf("Play any 2 sports for atleast 20 mins each"))
        )
    }

}