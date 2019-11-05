package stanevich.elizaveta.stateofhealthtracker.home.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.home.database.States

@BindingAdapter("stateImage")
fun ImageView.setStatesImage(item: States?) {
    item?.let {
        setImageResource(
            when (item.statesMood) {
                "+" -> R.drawable.image_button_excellent_full
                "-" -> R.drawable.image_button_satisfactorily_full
                else -> R.drawable.image_button_bad_full
            }
        )
    }
}