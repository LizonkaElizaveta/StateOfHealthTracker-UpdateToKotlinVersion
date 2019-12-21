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
import stanevich.elizaveta.stateofhealthtracker.databinding.DialogDateAndTimeBinding
import stanevich.elizaveta.stateofhealthtracker.home.database.States
import stanevich.elizaveta.stateofhealthtracker.utils.getDate
import stanevich.elizaveta.stateofhealthtracker.utils.getDateTimeValue
import stanevich.elizaveta.stateofhealthtracker.utils.getTime
import java.util.*

class DateAndTimeDialog(private val stateOfHealth: MutableLiveData<States?>) : DialogFragment() {
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogDateAndTimeBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_date_and_time,
                null,
                false
            )

        binding.apply {
            etDate = dialogEditTextDate.editText!!
            etDate.setText(getDate(Calendar.getInstance().timeInMillis))
            etDate.setOnClickListener {
                val dpd =
                    DatePickerFragment(DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val calendar =
                            DatePickerFragment.getCalendarDate(year, monthOfYear, dayOfMonth)
                        etDate.setText(getDate(calendar.timeInMillis))
                    })
                fragmentManager?.let { it1 -> dpd.show(it1, "DatePickerDialog") }

            }
            etTime = dialogEditTextTime.editText!!
            etTime.setText(getTime(Calendar.getInstance().timeInMillis))
            etTime.setOnClickListener {
                val tpd =
                    TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val calendar = TimePickerFragment.getCalendarTime(hourOfDay, minute)
                        etTime.setText(getTime(calendar.timeInMillis))
                    })
                fragmentManager?.let { it1 -> tpd.show(it1, "TimePickerDialog") }

            }
        }
        val builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
            .setTitle(R.string.dialogHeadline_choseDateAndTime)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                val states = stateOfHealth.value!!
                states.date = getDateTimeValue(etDate.text.toString(), etTime.text.toString()).time
                Log.d("mLog", states.toString())
                stateOfHealth.postValue(states)
                dialog!!.dismiss()
            }
            .setNegativeButton(R.string.btn_cancel) { _, _ ->
                dialog!!.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }

}

