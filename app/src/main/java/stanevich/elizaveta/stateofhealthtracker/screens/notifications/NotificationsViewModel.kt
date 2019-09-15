package stanevich.elizaveta.stateofhealthtracker.screens.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.NotificationsDatabaseDao

class NotificationsViewModel(
    private val database: NotificationsDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

}

