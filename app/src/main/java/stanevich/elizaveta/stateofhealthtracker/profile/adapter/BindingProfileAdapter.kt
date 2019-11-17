package stanevich.elizaveta.stateofhealthtracker.profile.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.profile.model.PopulateProfileData

@BindingAdapter("profileListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PopulateProfileData>?) {
    val adapter = recyclerView.adapter as ProfileAdapter
    adapter.submitList(data)
}

@BindingAdapter("titleProfileLink")
fun bindTitleLink(textView: TextView, resourceLink: String) {
    val resourceField = R.string::class.java.getDeclaredField(resourceLink)
    val resourceId = resourceField.getInt(resourceField)
    textView.text = textView.context.getText(resourceId)
}

@BindingAdapter("detailProfileLink")
fun bindDetailLink(textView: TextView, resourceLink: String) {
    val resourceField = R.string::class.java.getDeclaredField(resourceLink)
    val resourceId = resourceField.getInt(resourceField)
    textView.text = textView.context.getText(resourceId)
}

