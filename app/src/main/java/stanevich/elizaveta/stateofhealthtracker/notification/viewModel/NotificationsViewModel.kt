package stanevich.elizaveta.stateofhealthtracker.notification.viewModel

import androidx.lifecycle.ViewModel
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao

class NotificationsViewModel(
    database: NotificationsDatabaseDao
) : ViewModel() {

    val notifications = database.getAllNotifications()

}

