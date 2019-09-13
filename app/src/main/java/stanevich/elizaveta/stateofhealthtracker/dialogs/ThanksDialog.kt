package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R

class ThanksDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setMessage(R.string.dialogText_thanks)
            .setPositiveButton(R.string.dialogButton_next) { _, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
    }
}