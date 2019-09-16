package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.CustomDialogCategoryBinding

class CategoryDialog :
    DialogFragment() {
//    private val tonightNotification: MutableLiveData<Notifications?>
//    lateinit var etTime: EditText
//    lateinit var etDate: EditText
//    lateinit var rbMedication: RadioButton
//    lateinit var rbAppointment RadioButton

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: CustomDialogCategoryBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.custom_dialog_category,
                null,
                false
            )

        val builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
            .setTitle(R.string.dialogHeadline_category)
            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
                //                val states = stateOfHealth.value!!
//                states.statesDate = getDateTimeValue(etDate.text.toString(), etTime.text.toString())
//                Log.d("mLog", states.toString())
//                stateOfHealth.postValue(states)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialogButton_cancel) { _, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }
}
