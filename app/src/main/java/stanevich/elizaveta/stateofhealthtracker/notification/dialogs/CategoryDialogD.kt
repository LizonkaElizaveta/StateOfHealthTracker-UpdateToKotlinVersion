package stanevich.elizaveta.stateofhealthtracker.notification.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.CustomDialogCategoryBinding
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications

class CategoryDialogD(
    private val tonightNotification: MutableLiveData<Notifications?>,
    private val categoryText: TextView
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
            .setPositiveButton(R.string.btn_ok) { _, _ ->

                val checked = checkedRadioButtonListener(binding)

                val notification = tonightNotification.value!!
                notification.notificationsText = checked.text.toString()
                categoryText.text = notification.notificationsText
                dialog!!.dismiss()

//                fragmentManager?.let {
//                    TimePickerFragment(
//                        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//                            val calendar =
//                                TimePickerFragment.getCalendarTime(
//                                    hourOfDay,
//                                    minute
//                                )
//                            postValue(calendar, notification)
//                        }).show(it, "TimePickerDialog")
//                }
//                onStartTracking.invoke()

            }

            .setNegativeButton(R.string.btn_cancel) { _, _ ->
                dialog!!.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }

//    private fun postValue(
//        calendar: Calendar,
//        notification: Notifications
//    ) {
//        val time = getTime(calendar.timeInMillis)
//        notification.notificationsTime = time
//        // tonightNotification.postValue(notification)
//        Log.d("mLog", "From Dialog " + tonightNotification.value.toString())
//        Log.d("mLog", "From Dialog " + tonightNotification.value.toString())
//        onStartTracking.invoke()
//    }

    private fun checkedRadioButtonListener(binding: CustomDialogCategoryBinding): RadioButton {
        selectedId = binding.radioCategory.checkedRadioButtonId

        val checked = resources.getResourceEntryName(selectedId)

        radioButton = when (checked) {
            "rbMedication" -> binding.rbMedication
            "rbDoctor" -> binding.rbDoctor
            "rbAppointment" -> binding.rbAppointment
            else -> binding.rbOther
        }
        return radioButton
    }
}
