package stanevich.elizaveta.stateofhealthtracker.utils

import android.os.Build
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*


class DateConverters {
    private lateinit var newDate: Date
    @TypeConverter
    fun fromDate(date: Date): String? {

        var formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var updatedDate = date.toInstant().truncatedTo(ChronoUnit.MINUTES)

            val unroundedMinutes = updatedDate.getLong(ChronoField.INSTANT_SECONDS)

            val mod = unroundedMinutes % (5 * 60)
            updatedDate =
                updatedDate.plus(if (mod < (3 * 60)) -mod else (5 * 60) - mod, ChronoUnit.SECONDS)
            newDate = Date.from(updatedDate)
        } else {

            TODO("VERSION.SDK_INT < O")
        }

        return formatter.format(newDate)
    }

    @TypeConverter
    fun stringToDate(str: String): Date? {
        var formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.parse(str)
    }

    private fun dateRounding() {

    }

}