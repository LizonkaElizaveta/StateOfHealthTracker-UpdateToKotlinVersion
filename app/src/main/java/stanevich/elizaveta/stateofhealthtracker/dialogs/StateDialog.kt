package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.DialogCategoryStateBinding

class StateDialog(
    private val state: MutableLiveData<String>
) : DialogFragment() {
    private lateinit var emotion: String


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogCategoryStateBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_category_state,
                null,
                false
            )

        val builder = AlertDialog.Builder(context)

        resources.getStringArray(R.array.emotion_state).forEachIndexed { i, state ->
            val radioButton = getMyRadioBtn(i, state, i == 0)
            binding.radioCategory.addView(radioButton)
        }

        builder.setView(binding.root)
            .setTitle(R.string.title_emotion_state)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                binding.apply {
                    val selectID = radioCategory.checkedRadioButtonId
                    val r = radioCategory.getChildAt(selectID) as RadioButton
                    emotion = r.text.toString()
                }
                state.postValue(emotion)
                this.dismiss()
            }
            .setCancelable(false)

        return builder.create()
    }

    private fun getMyRadioBtn(id: Int, text: String, isChecked: Boolean): RadioButton {
        val radioButton = RadioButton(this.context)
        radioButton.apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.id = id
            setButtonDrawable(R.drawable.selector_dialog)
            this.text = text
            this.isChecked = isChecked
            overScrollMode = DrawerLayout.OVER_SCROLL_NEVER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                40f,
                context.resources.displayMetrics
            ).toInt()
            setBackgroundResource(R.color.transparent)
        }
        ViewCompat.setLayoutDirection(radioButton, ViewCompat.LAYOUT_DIRECTION_RTL)
        return radioButton
    }
}