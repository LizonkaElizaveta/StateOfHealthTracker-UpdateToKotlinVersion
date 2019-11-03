package stanevich.elizaveta.stateofhealthtracker.test.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.test.database.Test


@BindingAdapter("imgLink")
fun bindImageLink(imageView: ImageView, drawableLink: String) {
    val drawableField = R.drawable::class.java.getDeclaredField(drawableLink)
    val drawableId = drawableField.getInt(drawableField)
    imageView.setImageDrawable(imageView.context.getDrawable(drawableId))
}

@BindingAdapter("testListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Test>?) {
    val adapter = recyclerView.adapter as TestAdapter
    adapter.submitList(data)
}

@BindingAdapter("textLink")
fun bindTextLink(textView: TextView, resourceLink: String) {
    val resourceField = R.string::class.java.getDeclaredField(resourceLink)
    val resourceId = resourceField.getInt(resourceField)
    textView.text = textView.context.getText(resourceId)
}
