package stanevich.elizaveta.stateofhealthtracker

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
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

@BindingAdapter("testImage")
fun ImageView.bindTestImage(item: Test) {
    item.let {
        setImageResource(
            when (item.id) {
                1 -> R.drawable.background_test_figure
                2 -> R.drawable.background_test_burst_ball
                3 -> R.drawable.background_test_mole
                else -> R.drawable.background_test_target
            }
        )
    }
}

@BindingAdapter("testText")
fun Button.bindTestText(item: Test) {
    item.let {
        setText(
            when (item.id) {
                1 -> R.string.text_test_draw_figure
                2 -> R.string.text_test_burst_ball
                3 -> R.string.text_test_mole
                else -> R.string.text_test_target
            }
        )
    }
}