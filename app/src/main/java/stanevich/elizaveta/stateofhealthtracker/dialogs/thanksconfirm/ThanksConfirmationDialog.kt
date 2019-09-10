package stanevich.elizaveta.stateofhealthtracker.dialogs.thanksconfirm

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import java.util.*

class ThanksConfirmationDialog(private val stateOfHealth: MutableLiveData<States?>) :
    DialogFragment() {


    private lateinit var mListener: ThanksConfirmationDialogListener


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = context as ThanksConfirmationDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement ThanksConfirmationDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setMessage("Please, confirm the action")
            .setPositiveButton("Confirm") { _, _ ->
                val soh = stateOfHealth.value!!
                soh.statesDate = Date()
                Log.d("mLog", soh.toString())
                stateOfHealth.postValue(soh)
            }
            .setNegativeButton("Cancel") { _, _ ->
                Log.d("mLog", "cancel")
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