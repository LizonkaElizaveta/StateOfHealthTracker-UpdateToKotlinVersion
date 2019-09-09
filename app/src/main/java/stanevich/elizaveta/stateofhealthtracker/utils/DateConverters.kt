package stanevich.elizaveta.stateofhealthtracker.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*



class DateConverters {
    @TypeConverter
    fun fromDate(date: Date): String? {
        var formatter = SimpleDateFormat("dd.MM.yyyy HH:mm",Locale.getDefault())
        return formatter.format(date)
    }

    @TypeConverter
    fun stringToDate(str: String): Date? {
        var formatter = SimpleDateFormat("dd.MM.yyyy HH:mm",Locale.getDefault())
        return formatter.parse(str)
    }

}