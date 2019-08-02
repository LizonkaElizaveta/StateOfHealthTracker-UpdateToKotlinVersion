package stanevich.elizaveta.stateofhealthtracker

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.custom_dialog_thanks_confirmation.view.*


class ThanksConfirmationDialog{

    fun showDialog(activity: Activity, headline: String, dialogText: String) {
        val dialog = Dialog(activity, R.style.MyDialogStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_thanks_confirmation)
        val head = dialog.findViewById(R.id.dialogHeadline_thanks_confirmation) as TextView
        head.text = headline
        val text = dialog.findViewById(R.id.dialogText_thanks_confirmation) as TextView
        text.text = dialogText
        val btnNext = dialog.findViewById(R.id.dialogButton_next) as Button

        btnNext.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

}