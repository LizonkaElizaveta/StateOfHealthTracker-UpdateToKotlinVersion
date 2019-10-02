package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R
import java.util.*

class TimePickerFragment(private val listener: TimePickerDialog.OnTimeSetListener) :
    DialogFragment() {

    companion object {
        fun getCalendarTime(hourOfDay: Int, minute: Int): Calendar {
            val calendar = Calendar.getInstance()
            calendar.apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            return calendar
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hh = calendar.get(Calendar.HOUR_OF_DAY)
        val mm = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(activity!!, R.style.CustomDatePickerDialog, listener, hh, mm, true)

        timePickerDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            resources.getString(R.string.btn_ok),
            timePickerDialog
        )
        timePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            resources.getString(R.string.btn_cancel),
            timePickerDialog
        )

        return timePickerDialog
    }

}