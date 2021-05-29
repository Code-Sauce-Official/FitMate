package com.acash.fitmate.models

import com.acash.fitmate.R

class Community(
    val id: Int,
    val caption: String
) {
    companion object {
        fun getCommunities(): ArrayList<Community> = arrayListOf(
            Community(R.drawable.workout_pic, "Workout"),
            Community(R.drawable.yoga_pic, "Yoga"),
            Community(R.drawable.aerobics_pic, "Aerobics"),
            Community(R.drawable.cycling_pic, "Cycling"),
            Community(R.drawable.running_pic, "Running"),
            Community(R.drawable.swimming_pic, "Swimming"),
            Community(R.drawable.badminton_pic, "Badminton"),
            Community(R.drawable.football_pic, "Football"),
            Community(R.drawable.cricket_pic, "Cricket"),
            Community(R.drawable.basketball_pic, "Basketball"),
            Community(R.drawable.volleyball_pic, "Volleyball"),
            Community(R.drawable.tennis_pic, "Tennis"),
        )

        fun getCommunitiesName(): ArrayList<String> = arrayListOf(
            "Workout",
            "Yoga",
            "Aerobics",
            "Cycling",
            "Running",
            "Swimming",
            "Badminton",
            "Football",
            "Cricket",
            "Basketball",
            "Volleyball",
            "Tennis"
        )
    }
}