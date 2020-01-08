package stanevich.elizaveta.stateofhealthtracker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class DoubleArrayConverters {

    @TypeConverter
    fun fromDoubleArray(array: Array<Double>) : String? {
         return Gson().toJson(array)
    }

    @TypeConverter
    fun stringTODoubleArray(str:String) : Array<Double>?{
        val array = Gson().fromJson(str, Array<Double>::class.java) as Array<Double>
        return array
    }
}