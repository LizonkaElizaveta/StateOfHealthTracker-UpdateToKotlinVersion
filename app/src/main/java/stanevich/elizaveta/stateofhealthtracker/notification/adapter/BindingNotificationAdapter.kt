package stanevich.elizaveta.stateofhealthtracker.notification.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import stanevich.elizaveta.stateofhealthtracker.R
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

//@BindingAdapter("btnCheck")
//fun ImageView.setBtnCheck(imageView: ImageView, drawableLink: String) {
//    val drawableField = R.drawable::class.java.getDeclaredField(drawableLink)
//    val drawableId = drawableField.getInt(drawableField)
//    imageView.setImageDrawable(imageView.context.getDrawable(drawableId))
//}
//
//@BindingAdapter("btnUncheck")
//fun ImageView.setBtnUncheck(imageView: ImageView, drawableLink: String) {
//    val drawableField = R.drawable::class.java.getDeclaredField(drawableLink)
//    val drawableId = drawableField.getInt(drawableField)
//    imageView.setImageDrawable(imageView.context.getDrawable(drawableId))
//}