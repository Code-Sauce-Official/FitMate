package com.acash.fitmate.models

class User(
    val uid:String,
    val name:String,
    val gender:String,
    val dob:String,
    val yearOfBirth:Int,
    val state:String,
    val downloadUrlDp:String,
    val communities:ArrayList<String> = ArrayList(),
    val partners:ArrayList<String> = ArrayList()
){
    constructor() : this("","","","",0,"","",ArrayList<String>(),ArrayList<String>())
}