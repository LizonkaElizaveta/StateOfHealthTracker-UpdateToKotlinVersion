package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import stanevich.elizaveta.stateofhealthtracker.R

class TappingTestResultDialog(private val taps: Int, private val onDismiss: ()->Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inf = activity?.layoutInflater?.inflate(R.layout.dialog_tapping_test_result, null)

        val alert = AlertDialog.Builder(context!!)
            .setView(inf)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                dialog!!.dismiss()
                onDismiss()
            }
            .setCancelable(false)
            .create()

        val msg = inf?.findViewById(R.id.tv_thanks) as TextView?
        msg?.let {
            it.text = getString(R.string.dialog_text_tapping_result, taps)
        }

        return alert
    }
}