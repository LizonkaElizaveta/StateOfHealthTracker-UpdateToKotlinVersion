package stanevich.elizaveta.stateofhealthtracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.databases.entity.Notifications
import stanevich.elizaveta.stateofhealthtracker.test.adapter.TestAdapter
import stanevich.elizaveta.stateofhealthtracker.test.database.Test

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

@BindingAdapter("testListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Test>?) {
    val adapter = recyclerView.adapter as TestAdapter
    adapter.submitList(data)
}
