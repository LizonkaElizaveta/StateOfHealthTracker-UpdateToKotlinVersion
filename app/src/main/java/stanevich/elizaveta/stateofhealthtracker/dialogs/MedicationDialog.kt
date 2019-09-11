package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States

class MedicationDialog(private val stateOfHealth: MutableLiveData<States?>) : DialogFragment() {

//
//    private var binding: CustomDialogMedicationBinding =
//        DataBindingUtil.setContentView(activity, R.layout.custom_dialog_medication)
//    private var dialog: Dialog = Dialog(activity, R.style.MyDialogStyle)
//    private var etDate: EditText
//    private var etTime: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setView(R.layout.custom_dialog_medication)
//            .setMessage(R.string.dialogHeadline_choseDateAndTime)
            .setTitle(R.string.dialogHeadline_choseDateAndTime)
            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
                //                val soh = stateOfHealth.value!!
//                soh.statesDate = Date()
//                Log.d("mLog", soh.toString())
                dialog.dismiss()
//                stateOfHealth.postValue(soh)
            }
            .setNegativeButton(R.string.dialogButton_cancel) { _, _ ->
                dialog.dismiss()
            }
            .create()
    }
//
//
//    init {
////        configureDialog(dialog)
//        binding.apply {
//            etDate = dialogEditTextDate.editText!!
//            etTime = dialogEditTextTime.editText!!
//            dialogButtonOk.setOnClickListener { dialog.dismiss() }
//            dialogButtonCancel.setOnClickListener { dialog.dismiss() }
//        }
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


}