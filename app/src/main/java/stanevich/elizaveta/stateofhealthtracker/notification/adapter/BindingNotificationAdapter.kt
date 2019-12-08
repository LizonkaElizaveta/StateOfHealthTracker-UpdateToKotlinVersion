package stanevich.elizaveta.stateofhealthtracker.notification.adapter

import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.model.CheckBoxModel

@BindingAdapter("notificationCategory")
fun TextView.setNotificationCategory(item: Notifications?) {
    item?.let {
        text = item.notificationsCategory
    }
}

@BindingAdapter("notificationTime")
fun TextView.setNotificationTime(item: Notifications?) {
    item?.let {
        text = item.notificationsTime
    }
}

@BindingAdapter("btnDrawable")
fun setBtnDrawable(checkBox: CheckBox, drawableLink: String) {
    val drawableField = R.drawable::class.java.getDeclaredField(drawableLink)
    val drawableId = drawableField.getInt(drawableField)
    checkBox.buttonDrawable = checkBox.context.getDrawable(drawableId)
}

@BindingAdapter("checkBoxListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CheckBoxModel>?) {
    val adapter = recyclerView.adapter as CheckBoxModelAdapter
    adapter.submitList(data)
}