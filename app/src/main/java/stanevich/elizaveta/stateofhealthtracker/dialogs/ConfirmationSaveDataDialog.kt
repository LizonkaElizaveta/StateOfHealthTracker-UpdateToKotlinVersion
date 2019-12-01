package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R

class ConfirmationSaveDataDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setView(R.layout.dialog_confirmation_save_data)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                dialog!!.dismiss()
            }
            .setCancelable(false)
            .create()
    }
}