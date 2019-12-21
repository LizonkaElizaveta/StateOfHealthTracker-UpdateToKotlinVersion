package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R

class TestResultDialog(private val onDismiss: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inf = activity?.layoutInflater?.inflate(R.layout.dialog_common_test_result, null)

        return AlertDialog.Builder(context!!)
            .setView(inf)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                dialog!!.dismiss()
                onDismiss()
            }
            .setCancelable(false)
            .create()
    }
}