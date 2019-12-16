package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R

class DataMiningDialog(
    private val onPositiveBtn: () -> Unit,
    private val onNegativeBtn: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setView(R.layout.dialog_mining)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                onPositiveBtn()
                this.dismiss()
            }
            .setNegativeButton(R.string.btn_cancel) { _, _ ->
                onNegativeBtn()
                this.dismiss()
            }
            .setCancelable(false)
            .create()
    }
}