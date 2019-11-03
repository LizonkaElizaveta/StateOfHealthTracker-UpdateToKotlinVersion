package stanevich.elizaveta.stateofhealthtracker.notification.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications

@BindingAdapter("notificationCategory")
fun TextView.setNotificationCategory(item: Notifications?) {
    item?.let {
        text = item.notificationsText
    }
}

@BindingAdapter("notificationTime")
fun TextView.setNotificationTime(item: Notifications?) {
    item?.let {
        text = item.notificationsTime
    }
}