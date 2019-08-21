package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.custom_dialog_medication.view.*
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.CustomDialogMedicationBinding

class MedicationDialog(activity: Activity) {
    private var binding: CustomDialogMedicationBinding = DataBindingUtil.setContentView(activity, R.layout.custom_dialog_medication)
    private var dialog: Dialog = Dialog(activity, R.style.MyDialogStyle)
    private var etDate: EditText
    private var etTime: EditText

    init {
//        configureDialog(dialog)
        binding.apply {
            etDate = dialogEditTextDate.editText!!
            etTime = dialogEditTextTime.editText!!
            dialogButtonOk.setOnClickListener {  dialog.dismiss()  }
            dialogButtonCancel.setOnClickListener { dialog.dismiss()  }
        }
        configureDialog(dialog)
        etDate.setText("12 June 2019")
        etTime.setText("15:15")
    }

    fun showDialog() {
        dialog.show()
    }

    private fun configureDialog(dialog: Dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_medication)
    }


}