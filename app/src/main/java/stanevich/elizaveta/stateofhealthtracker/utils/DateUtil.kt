package stanevich.elizaveta.stateofhealthtracker.utils

import java.text.SimpleDateFormat
import java.util.*


/* Spanned - An interface for text that has formatting attached to it. */


fun getDate(date: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun getTime(time: Long): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun getDateTimeValue(date: String, time: String): Date {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.parse("$date $time")
}

fun getDetailDate(date: Long): String {
    val dateFormat = SimpleDateFormat("E, dd MMM, yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

