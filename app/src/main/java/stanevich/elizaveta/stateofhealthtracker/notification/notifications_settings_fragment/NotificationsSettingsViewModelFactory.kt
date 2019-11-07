package stanevich.elizaveta.stateofhealthtracker.notification.notifications_settings_fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao

class NotificationsSettingsViewModelFactory(
    private val dataSource: NotificationsDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsSettingsViewModel::class.java)) {
            return NotificationsSettingsViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}