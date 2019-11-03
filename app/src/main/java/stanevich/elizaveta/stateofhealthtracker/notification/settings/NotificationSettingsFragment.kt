package stanevich.elizaveta.stateofhealthtracker.notification.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationSettingsBinding

class NotificationSettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotificationSettingsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notification_settings,
            container,
            false
        )

        return binding.root
    }


}