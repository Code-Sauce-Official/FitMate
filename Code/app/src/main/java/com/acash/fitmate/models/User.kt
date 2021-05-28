package com.acash.fitmate.models

class User(
    val uid:String,
    val name:String,
    val gender:String,
    val dob:String,
    val state:String,
    val downloadUrlDp:String,
    val communities:ArrayList<String> = ArrayList(),
    val partners:ArrayList<String> = ArrayList()
){
    constructor() : this("","","","","","",ArrayList<String>(),ArrayList<String>())
}