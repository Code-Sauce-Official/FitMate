package com.acash.fitmate.models

import java.util.*

data class Inbox(
    val msg:String,
    var from:String,
    var name:String,
    var image:String,
    var count:Int,
    val time: Date = Date()
){
    constructor():this("","","","",0, Date())
}