package com.acash.fitmate.models

import android.content.Context
import com.acash.fitmate.utils.formatAsHeader
import java.util.*

interface ChatEvent {
    val sentAt: Date
}

data class Messages(
    val msg: String,
    var senderId: String,
    val msgId: String,
    val type: String = "Text",
    val status: Int = 1,
    val liked: Boolean = false,
    override val sentAt: Date = Date()
) : ChatEvent {
    constructor() : this("", "", "", "Text", 1, false, Date())
}

data class DateHeader(
    val context: Context,
    override val sentAt: Date = Date()
) : ChatEvent {
    val date: String = sentAt.formatAsHeader(context)
}