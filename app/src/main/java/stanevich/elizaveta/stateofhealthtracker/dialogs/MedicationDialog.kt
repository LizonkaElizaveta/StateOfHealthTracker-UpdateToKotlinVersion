package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import stanevich.elizaveta.stateofhealthtracker.R

class MedicationDialog(activity: Activity) {
    private var dialog: Dialog = Dialog(activity, R.style.MyDialogStyle)
    private var head: TextView
    private var tilDate: TextInputLayout
    private var tilTime: TextInputLayout
    private var etDate: EditText
    private var etTime: EditText
    private var btnOk : Button
    private var btnCancel: Button
    private var container: View

    init {
        configureDialog(dialog)
        container = View.inflate(activity,R.layout.custom_dialog_medication,null)
        head = dialog.findViewById(R.id.dialogHeadline_medication)

        tilDate = dialog.findViewById(R.id.dialogEditText_date)
        etDate = tilDate.editText!!

        etDate.setText("12 June 2019")

        tilTime = dialog.findViewById(R.id.dialogEditText_time)
        etTime = tilTime.editText!!

        etTime.setText("15:15")

        btnOk = dialog.findViewById(R.id.dialogButton_ok)
        btnOk.setOnClickListener {  }
        btnCancel = dialog.findViewById(R.id.dialogButton_cancel)
        btnCancel.setOnClickListener { dialog.dismiss() }
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