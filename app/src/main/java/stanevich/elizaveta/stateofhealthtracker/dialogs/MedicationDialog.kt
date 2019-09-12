package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import stanevich.elizaveta.stateofhealthtracker.databinding.CustomDialogMedicationBinding
import stanevich.elizaveta.stateofhealthtracker.utils.getDate
import java.util.*

class MedicationDialog(private val stateOfHealth: MutableLiveData<States?>) : DialogFragment() {
    lateinit var etDate: EditText
    lateinit var etTime: EditText
    lateinit var calendar: Calendar

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
                        calendar = Calendar.getInstance()
                        calendar.apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, monthOfYear)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }
                        etDate.setText(getDate(calendar.timeInMillis))
                    })
                dpd.show(fragmentManager,"DatePickerDialog")
            }
            etTime = dialogEditTextTime.editText!!

        }
        val builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
            .setTitle(R.string.dialogHeadline_choseDateAndTime)
            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialogButton_cancel) { _, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)


        return builder.create()
    }
}


//                        return AlertDialog.Builder(context!!)
//                            .setView(R.layout.custom_dialog_medication)
////            .setMessage(R.string.dialogHeadline_choseDateAndTime)
//                            .setTitle(R.string.dialogHeadline_choseDateAndTime)
//                            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
//                                val datePicker =
//                                    DatePickerFragment(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//
//                                    })
//                                //                val soh = stateOfHealth.value!!
////                soh.statesDate = Date()
////                Log.d("mLog", soh.toString())
//                                builder.dismiss()
////                stateOfHealth.postValue(soh)
//                            }
//                            .setNegativeButton(R.string.dialogButton_cancel) { _, _ ->
//                                builder.dismiss()
//                            }
//                            .create()
//                    }
////
//

//        configureDialog(dialog)
//        etDate.setText("12 June 2019")
//        etTime.setText("15:15")
//    }
//
//    fun showDialog() {
//        dialog.show()
//    }
//
//    private fun configureDialog(dialog: Dialog) {
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_dialog_medication)
//    }


