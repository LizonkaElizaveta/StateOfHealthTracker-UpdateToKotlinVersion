package stanevich.elizaveta.stateofhealthtracker.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import java.text.SimpleDateFormat
import java.util.*


/* Spanned - An interface for text that has formatting attached to it. */

fun formatStates(states: List<States>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("StatesDB")
        states.forEach {
            append("<br>")
            append("id")
            append("\t${it.statesId}<br>")
//            append("\t${convertLongToDateString(it.statesTime)}<br>")
            append("\t${it.statesMood}<br>")
            append("\t${it.statesPill}<br>")
            append("\t${it.statesDiskinezia}<br>")

        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun getDate(date: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun getTime(time: Long): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun getDateTimeValue(date: String , time: String): Date {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.parse("$date $time")
}
