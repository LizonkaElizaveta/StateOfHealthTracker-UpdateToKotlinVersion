package stanevich.elizaveta.stateofhealthtracker.utils

import android.content.Context
import android.net.ConnectivityManager

object ConnectivityUtil {
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isDefaultNetworkActive
    }
}