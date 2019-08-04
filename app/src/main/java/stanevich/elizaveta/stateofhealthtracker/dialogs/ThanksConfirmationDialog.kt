package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import stanevich.elizaveta.stateofhealthtracker.R

class ThanksConfirmationDialog(activity: Activity) {
    private var dialog: Dialog = Dialog(activity, R.style.MyDialogStyle)
    private var head: TextView
    private var text: TextView
    private var btnNext: TextView

    init {
        configureDialog(dialog)
        head = dialog.findViewById(R.id.dialogHeadline_thanks_confirmation)
        text = dialog.findViewById(R.id.dialogText_thanks_confirmation)
        btnNext = dialog.findViewById(R.id.dialogButton_next)
        btnNext.setOnClickListener { dialog.dismiss() }
    }

    fun showDialog(headline: String, dialogText: String) {
        head.text = headline
        text.text = dialogText
        dialog.show()
    }

    private fun configureDialog(dialog: Dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_thanks_confirmation)
    }


}