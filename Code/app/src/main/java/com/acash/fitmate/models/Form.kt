package com.acash.fitmate.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Form(
    val formId: String,
    val uid: String,
    val category: String,
    val state: String,
    val name: String,
    val gender: String,
    @field:JvmField
    val isGenderSpecific: Boolean,
    val yearOfBirth: Int,
    val motive: String,
    @ServerTimestamp
    val createdDate: Date? = null
) {
    constructor() : this("", "", "", "", "", "", false, 0, "",null)
}