package stanevich.elizaveta.stateofhealthtracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import stanevich.elizaveta.stateofhealthtracker.databases.entity.Notifications

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