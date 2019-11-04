package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R
import java.util.*

class DatePickerFragment(private val listener: DatePickerDialog.OnDateSetListener) :
    DialogFragment() {

    companion object {
        fun getCalendarDate(
            year: Int,
            monthOfYear: Int,
            dayOfMonth: Int
        ): Calendar {
            val calendar = Calendar.getInstance()
            calendar.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            return calendar
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val yy = calendar.get(Calendar.YEAR)
        val mm = calendar.get(Calendar.MONTH)
        val dd = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(activity!!, listener, yy, mm, dd)
        datePickerDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            resources.getString(R.string.btn_ok),
            datePickerDialog
        )

        datePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            resources.getString(R.string.btn_cancel),
            datePickerDialog
        )

        return datePickerDialog
    }

}