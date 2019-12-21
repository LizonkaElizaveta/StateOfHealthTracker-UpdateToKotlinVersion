package stanevich.elizaveta.stateofhealthtracker.network.dto

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase

data class SendWrapper(
    val userId: Int = getUserId(),
    val timestamp: Long = 0,
    val testType: String? = null,
    val data: DataWrapper = DataWrapper()
)

fun getUserId(): Int = CoroutineScope(Dispatchers.IO).run {
    return if (context != null) {
        ProfileDatabase.getInstance(context!!).profileDatabaseDao.getLastUser()?.phone?.toInt()
            ?: 0
    } else 0
}