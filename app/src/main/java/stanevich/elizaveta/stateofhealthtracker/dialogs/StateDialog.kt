package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
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

        builder.setView(binding.root)
            .setTitle(R.string.dialog_emotion_title)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                binding.apply {
                    val selectID = radioCategory.checkedRadioButtonId
                    val r = radioCategory.getChildAt(selectID) as? RadioButton
                    emotion = r?.text.toString()
                }
                state.postValue(emotion)
                this.dismiss()
            }
            .setCancelable(false)

        return builder.create()
    }
}