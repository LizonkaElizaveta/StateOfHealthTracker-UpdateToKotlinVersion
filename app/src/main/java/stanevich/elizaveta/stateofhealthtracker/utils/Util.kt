package stanevich.elizaveta.stateofhealthtracker.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("dd.MM.yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}


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
