package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import stanevich.elizaveta.stateofhealthtracker.databinding.CustomDialogMedicationBinding
import stanevich.elizaveta.stateofhealthtracker.utils.getDate
import stanevich.elizaveta.stateofhealthtracker.utils.getDateTimeValue
import stanevich.elizaveta.stateofhealthtracker.utils.getTime

class MedicationDialog(private val stateOfHealth: MutableLiveData<States?>) : DialogFragment() {
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: CustomDialogMedicationBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.custom_dialog_medication,
                null,
                false
            )

        binding.apply {
            etDate = dialogEditTextDate.editText!!
            etDate.setOnClickListener {
                val dpd =
                    DatePickerFragment(DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val calendar =
                            DatePickerFragment.getCalendarDate(year, monthOfYear, dayOfMonth)
                        etDate.setText(getDate(calendar.timeInMillis))
                    })
                dpd.show(fragmentManager, "DatePickerDialog")

            }
            etTime = dialogEditTextTime.editText!!
            etTime.setOnClickListener {
                val tpd =
                    TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val calendar = TimePickerFragment.getCalendarTime(hourOfDay, minute)
                        etTime.setText(getTime(calendar.timeInMillis))
                    })
                tpd.show(fragmentManager, "TimePickerDialog")

            }
        }
        val builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
            .setTitle(R.string.dialogHeadline_choseDateAndTime)
            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
                val states = stateOfHealth.value!!
                states.statesDate = getDateTimeValue(etDate.text.toString(), etTime.text.toString())
                Log.d("mLog", states.toString())
                stateOfHealth.postValue(states)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialogButton_cancel) { _, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }

}

