package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R

class PolicyDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setView(R.layout.dialog_policy)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                dialog!!.dismiss()
            }
            .setCancelable(false)
            .create()
    }
}