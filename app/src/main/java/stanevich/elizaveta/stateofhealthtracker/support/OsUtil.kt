package stanevich.elizaveta.stateofhealthtracker.support

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log
import stanevich.elizaveta.stateofhealthtracker.BuildConfig

object OsUtil {

    @JvmStatic
    fun getDeviceManufacturer(): String = Build.MANUFACTURER

    @JvmStatic
    fun getDeviceName(): String = Build.MODEL

    @JvmStatic
    fun getSDKVersion(): Int = Build.VERSION.SDK_INT

    @JvmStatic
    fun getOsVersionNum(): String = Build.VERSION.RELEASE

    @JvmStatic
    fun getDevice(): String = Build.DEVICE


    @JvmStatic
    fun getVersionCode(inContext: Context?): Int {
        inContext?.let { context ->
            try {
                return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                Log.e("error", e.toString())
            }
        }

        return -1
    }
}