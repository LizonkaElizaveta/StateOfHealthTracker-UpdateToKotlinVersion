package stanevich.elizaveta.stateofhealthtracker.notification.adapter

import android.widget.CheckBox
import android.widget.ImageView
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

@BindingAdapter("notificationIcon")
fun bindNotificationIcon(imageView: ImageView, category: String) {
    val context = imageView.context

    val drawable = when (category) {
        context.getString(R.string.radioButton_doctors_appointment) ->
            context.getDrawable(R.drawable.notification_ic_doctor)
        context.getString(R.string.radioButton_medication) ->
            context.getDrawable(R.drawable.notification_ic_pill)
        context.getString(R.string.radioButton_state_tracker) ->
            context.getDrawable(R.drawable.notification_ic_evaluation)
        else -> context.getDrawable(R.drawable.notification_ic_other)
    }

    imageView.setImageDrawable(drawable)
}

@BindingAdapter("notificationDate")
fun TextView.setNotificationDate(item: Notifications?) {
    item?.let {
        text = item.notificationsDate
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