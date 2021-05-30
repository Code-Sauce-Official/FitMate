package com.acash.fitmate.utils

import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import com.acash.fitmate.R
import java.text.SimpleDateFormat
import java.util.*

private fun getCurrentLocale(context: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}

fun Date.isThisWeek(): Boolean {
    val thisCalendar = Calendar.getInstance()
    val thisWeek = thisCalendar.get(Calendar.WEEK_OF_YEAR)
    val thisYear = thisCalendar.get(Calendar.YEAR)

    val calendar = Calendar.getInstance()
    calendar.time = this
    val week = calendar.get(Calendar.WEEK_OF_YEAR)
    val year = calendar.get(Calendar.YEAR)

    return year == thisYear && week == thisWeek
}

fun Date.isToday(): Boolean {
    return DateUtils.isToday(this.time)
}

fun Date.isYesterday(): Boolean {
    val yestercal = Calendar.getInstance()
    yestercal.add(Calendar.DAY_OF_YEAR, -1)

    return yestercal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
            && yestercal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
}

fun Date.isThisYear(): Boolean {
    return Calendar.getInstance().get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
}

fun Date.isSameDayAs(date: Date): Boolean {
    return this.calendar.get(Calendar.DAY_OF_YEAR) == date.calendar.get(Calendar.DAY_OF_YEAR)
}

val Date.calendar: Calendar
    get() {
        val cal = Calendar.getInstance()
        cal.time = this
        return cal
    }

fun Date. formatAsTime(): String {
    val hour = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
    val minute = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')
    return "$hour:$minute"
}

fun Date.formatAsYesterday(c: Context): String {
    return c.getString(R.string.yesterday)
}

fun Date.formatAsWeekDay(c: Context): String {
    val s = { id: Int -> c.getString(id) }

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> s(R.string.monday)
        Calendar.TUESDAY -> s(R.string.tuesday)
        Calendar.WEDNESDAY -> s(R.string.wednesday)
        Calendar.THURSDAY -> s(R.string.thursday)
        Calendar.FRIDAY -> s(R.string.friday)
        Calendar.SATURDAY -> s(R.string.saturday)
        Calendar.SUNDAY -> s(R.string.sunday)
        else -> {
            SimpleDateFormat("d LLL", getCurrentLocale(c)).format(this)
        }
    }
}

fun Date.formatAsFull(context: Context, abbreviated: Boolean = false): String {
    val month = if (abbreviated) "LLL" else "LLLL"

    return SimpleDateFormat("d $month YYYY", getCurrentLocale(context))
        .format(this)
}

fun Date.formatAsListItem(context: Context): String {
    val currentLocale = getCurrentLocale(context)

    return when {
        isToday() -> formatAsTime()
        isYesterday() -> formatAsYesterday(context)
        isThisWeek() -> formatAsWeekDay(context)
        isThisYear() -> {
            SimpleDateFormat("d LLL", currentLocale).format(this)
        }
        else -> {
            formatAsFull(context, abbreviated = true)
        }
    }
}

fun Date.formatAsHeader(context: Context): String {
    return when {
        isToday() -> context.getString(R.string.today)
        isYesterday() -> formatAsYesterday(context)
        isThisWeek() -> formatAsWeekDay(context)
        isThisYear() -> {
            SimpleDateFormat("d LLLL", getCurrentLocale(context))
                .format(this)
        }
        else -> {
            formatAsFull(context, abbreviated = false)
        }
    }
}