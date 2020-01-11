package stanevich.elizaveta.stateofhealthtracker.notification.adapter

import android.os.Build
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
        text = item.category
    }
}

@BindingAdapter("notificationTime")
fun TextView.setNotificationTime(item: Notifications?) {
    item?.let {
        text = item.time
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
    item?.let { notification ->
        val daysOfWeek = notification.repeat
        if (daysOfWeek.all { it }) {
            text = context.getString(R.string.EveryDay)
        } else if (daysOfWeek.any { it }) {
            text = daysOfWeek.mapIndexed { index, b ->
                if (b) {
                    when (index) {
                        0 -> "ПН"
                        1 -> "ВТ"
                        2 -> "СР"
                        3 -> "ЧТ"
                        4 -> "ПТ"
                        5 -> "СБ"
                        6 -> "ВС"
                        else -> ""
                    }
                } else ""
            }.filter { it.isNotEmpty() }.joinToString(separator = ", ")
        } else {
            text = notification.date
        }
    }
}

@BindingAdapter("btnDrawable")
fun setBtnDrawable(checkBox: CheckBox, drawableLink: String) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        val text = when (drawableLink) {
            "notification_settings_ic_monday" -> "ПН"
            "notification_settings_ic_tuesday" -> "ВТ"
            "notification_settings_ic_wednesday" -> "СР"
            "notification_settings_ic_thursday" -> "ЧТ"
            "notification_settings_ic_friday" -> "ПТ"
            "notification_settings_ic_saturday" -> "СБ"
            "notification_settings_ic_sunday" -> "ВС"
            else -> ""
        }
        checkBox.text = text
    } else {
        val drawableField = R.drawable::class.java.getDeclaredField(drawableLink)
        val drawableId = drawableField.getInt(drawableField)
        checkBox.buttonDrawable = checkBox.context.getDrawable(drawableId)
    }
}

@BindingAdapter("checkBoxListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CheckBoxModel>?) {
    val adapter = recyclerView.adapter as CheckBoxModelAdapter
    adapter.submitList(data)
}