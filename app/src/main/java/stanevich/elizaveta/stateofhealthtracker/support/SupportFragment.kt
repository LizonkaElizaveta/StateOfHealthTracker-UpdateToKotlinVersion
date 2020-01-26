package stanevich.elizaveta.stateofhealthtracker.support

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentSupportBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.PolicyDialog
import stanevich.elizaveta.stateofhealthtracker.support.OsUtil.getDevice
import stanevich.elizaveta.stateofhealthtracker.support.OsUtil.getDeviceManufacturer
import stanevich.elizaveta.stateofhealthtracker.support.OsUtil.getDeviceName
import stanevich.elizaveta.stateofhealthtracker.support.OsUtil.getOsVersionNum
import stanevich.elizaveta.stateofhealthtracker.support.OsUtil.getSDKVersion
import stanevich.elizaveta.stateofhealthtracker.support.OsUtil.getVersionCode


class SupportFragment : Fragment() {

    companion object {
        const val ATI_MOBILE_SUPPORT_EMAIL = "liza_soft_1313@mail.ru"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSupportBinding.inflate(inflater)

        binding.tvSupport.setOnClickListener {
            sendEmail()
        }

        binding.tvPolicy.setOnClickListener {
            fragmentManager?.let { fragmentManager ->
                PolicyDialog().show(
                    fragmentManager,
                    "PolicyDialog"
                )
            }
        }

        return binding.root
    }


    private fun sendEmail() {
        val context: Context = view!!.context
        val emailIntent =
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$ATI_MOBILE_SUPPORT_EMAIL"))

        emailIntent.apply {
            putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.feedback_subject_pattern))
            putExtra(Intent.EXTRA_TEXT, getApplicationVersion())
        }

        try {
            view!!.context.startActivity(
                Intent.createChooser(
                    emailIntent,
                    context.getString(R.string.send_email)
                )
            )
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.not_install_email_app, Toast.LENGTH_SHORT).show()
        }
    }


    private fun getApplicationVersion(): String? {
        if (view == null) return null
        val context: Context = view!!.context
        val osVersionNum = getOsVersionNum()
        val sdkVersion = getSDKVersion()
        val device = getDevice()
        val manufacturer = getDeviceManufacturer()
        val deviceName = getDeviceName()
        val versionCode = getVersionCode(context)
        val feedbackPattern = resources.getString(R.string.feedback_app_version_pattern)
        return String.format(
            feedbackPattern,
            osVersionNum,
            sdkVersion,
            manufacturer,
            deviceName,
            device,
            versionCode
        )
    }
}