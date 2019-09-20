package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.entity.Notifications
import stanevich.elizaveta.stateofhealthtracker.databinding.CustomDialogCategoryBinding
import stanevich.elizaveta.stateofhealthtracker.utils.getTime
import java.util.*

class CategoryDialog(
    private val tonightNotification: MutableLiveData<Notifications?>,
    private val onStartTracking: () -> Unit
) : DialogFragment() {


    private lateinit var radioButton: RadioButton
    private var selectedId: Int = 0


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

                val checked = checkedRadioButtonListener(binding)

                val notification = tonightNotification.value!!
                notification.notificationsText = checked.text.toString()
                dialog!!.dismiss()

                fragmentManager?.let {
                    TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val calendar = TimePickerFragment.getCalendarTime(hourOfDay, minute)
                        postValue(calendar, notification)
                    }).show(it, "TimePickerDialog")
                }
            }

            .setNegativeButton(R.string.dialogButton_cancel) { _, _ ->
                dialog!!.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }

    private fun postValue(
        calendar: Calendar,
        notification: Notifications
    ) {
        val time = getTime(calendar.timeInMillis)
        notification.notificationsTime = time
        // tonightNotification.postValue(notification)
        Log.d("mLog", "From Dialog " + tonightNotification.value.toString())
        Log.d("mLog", "From Dialog " + tonightNotification.value.toString())
        onStartTracking.invoke()
    }

    private fun checkedRadioButtonListener(binding: CustomDialogCategoryBinding): RadioButton {
        selectedId = binding.radioCategory.checkedRadioButtonId

        val checked = resources.getResourceEntryName(selectedId)

        radioButton = if (checked == "rbMedication") {
            binding.rbMedication
        } else
            binding.rbAppointment
        return radioButton
    }
}
