package stanevich.elizaveta.stateofhealthtracker.home.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.home.database.States

@BindingAdapter("stateImage")
fun ImageView.setStatesImage(item: States?) {
    item?.let {
        setImageResource(
            when (item.mood) {
                "+" -> R.drawable.home_smile_excellent_full
                "-" -> R.drawable.home_smile_satisfactorily_full
                else -> R.drawable.home_smile_bad_full
            }
        )
    }
}