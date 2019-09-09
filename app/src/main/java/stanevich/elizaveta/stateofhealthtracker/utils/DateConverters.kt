package stanevich.elizaveta.stateofhealthtracker.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


class DateConverters {
    private lateinit var newDate: Date
    @TypeConverter
    fun fromDate(date: Date): String? {

        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val c = Calendar.getInstance()
        c.time = date
        val minute = 1000 * 60
        c.timeInMillis = (c.timeInMillis / minute) * minute

        var updatedDate = c.timeInMillis

        val unroundedMinutes = c.get(Calendar.MINUTE)

        val mod = (unroundedMinutes % 5).toLong()
        updatedDate = updatedDate.plus((if (mod < 3) -mod else 5 - mod) * minute)
        newDate = Date(updatedDate)

        return formatter.format(newDate)
    }

    @TypeConverter
    fun stringToDate(str: String): Date? {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.parse(str)
    }
}