package stanevich.elizaveta.stateofhealthtracker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntArrayListConverter {
    @TypeConverter
    fun fromString(value: String): BooleanArray {
        val type = object : TypeToken<BooleanArray>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromArrayList(list: BooleanArray): String {
        val type = object : TypeToken<BooleanArray>() {}.type
        return Gson().toJson(list, type)
    }
}