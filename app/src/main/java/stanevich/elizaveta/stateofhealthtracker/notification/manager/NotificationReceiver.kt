package stanevich.elizaveta.stateofhealthtracker.notification.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import stanevich.elizaveta.stateofhealthtracker.notification.createCategoryNotification
import java.util.*

class NotificationReceiver : BroadcastReceiver() {
    companion object {
        const val CATEGORY = "category"
        const val ID = "id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        createCategoryNotification(
            intent.getIntExtra(ID, Random().nextInt()),
            intent.getStringExtra(CATEGORY),
            context
        )
    }
}