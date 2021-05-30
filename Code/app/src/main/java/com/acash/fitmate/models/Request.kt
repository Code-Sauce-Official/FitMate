package com.acash.fitmate.models

class Request(
    val uid:String,
    val name:String,
    val yearOfBirth:Int,
    val gender:String,
    val state:String,
    val downloadUrlDp:String
){
    constructor():this("","",0,"","","")
}