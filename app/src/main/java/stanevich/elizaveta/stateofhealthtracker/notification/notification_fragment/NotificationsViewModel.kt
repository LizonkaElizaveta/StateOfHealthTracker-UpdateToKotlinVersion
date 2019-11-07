package stanevich.elizaveta.stateofhealthtracker.notification.notification_fragment

import androidx.lifecycle.ViewModel
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao

class NotificationsViewModel(
    database: NotificationsDatabaseDao
) : ViewModel() {

    val notifications = database.getAllNotifications()

}

