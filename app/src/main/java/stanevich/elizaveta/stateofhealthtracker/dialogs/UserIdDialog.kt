package stanevich.elizaveta.stateofhealthtracker.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.DialogUserIdBinding
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModel

class UserIdDialog(
    private val profileViewModel: ProfileViewModel
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogUserIdBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.dialog_user_id,
                null,
                false
            )

        binding.profileViewModel = profileViewModel

        val dialog = AlertDialog.Builder(context!!)
            .setView(binding.root)
            .setPositiveButton(R.string.btn_ok) { _, _ ->
                if (checkPhone()) {
                    profileViewModel.phone.removeObservers(this)
                    profileViewModel.saveUserData()
                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "Телефон должен быть введён",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setCancelable(true)
            .create()


        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = false

            profileViewModel.phone.observe(this, Observer {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = checkPhone()
            })
        }

        return dialog
    }

    private fun checkPhone() =
        !profileViewModel.phone.value.isNullOrEmpty() && profileViewModel.phone.value!! != "0"
}