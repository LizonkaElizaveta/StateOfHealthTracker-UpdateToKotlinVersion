package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States

class ThanksConfirmationDialog(
    private val stateOfHealth: MutableLiveData<States?>,
    private val message: String
) :
    DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setMessage(message)
            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
                //                val soh = stateOfHealth.value!!
//                soh.statesDate = Date()
//                Log.d("mLog", soh.toString())
                dialog.dismiss()
//                stateOfHealth.postValue(soh)
            }
            .create()
    }


//    init {
//        configureDialog(dialog)
//
//        text = dialog.findViewById(R.id.dialogText_thanks_confirmation)
//        btnNext = dialog.findViewById(R.id.dialogButton_next)
//        btnNext.setOnClickListener { dialog.dismiss() }
//    }
//
//    fun showDialog(headline: String, dialogText: String) {
//        text.text = dialogText
//        dialog.show()
//    }
//
//    private fun configureDialog(dialog: Dialog) {
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_dialog_thanks_confirmation)
//    }


}