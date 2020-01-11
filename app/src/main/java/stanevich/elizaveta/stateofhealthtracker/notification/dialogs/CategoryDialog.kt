package stanevich.elizaveta.stateofhealthtracker.notification.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.DialogCategoryBinding
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications

class CategoryDialog(
    private val tonightNotification: MutableLiveData<Notifications?>,
    private val categoryText: TextView
) : DialogFragment() {



    private var selectedId: Int = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogCategoryBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_category,
                null,
                false
            )


        val builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
            .setPositiveButton(R.string.btn_ok) { _, _ ->

                val checkedText = checkedRadioButtonListener(binding)

                val notification = tonightNotification.value!!
                notification.notificationsCategory = checkedText
                categoryText.text = notification.notificationsCategory
                dialog!!.dismiss()

            }

            .setNegativeButton(R.string.btn_cancel) { _, _ ->
                dialog!!.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }

    private fun checkedRadioButtonListener(binding: DialogCategoryBinding): String {
        selectedId = binding.radioCategory.checkedRadioButtonId

        return when (resources.getResourceEntryName(selectedId)) {
            "rbMedication" -> binding.rbMedication.text.toString()
            "rbDoctor" -> binding.rbDoctor.text.toString()
            "rbAppointment" -> binding.rbAppointment.text.toString()
            else -> "Категория..."
        }
    }
}
