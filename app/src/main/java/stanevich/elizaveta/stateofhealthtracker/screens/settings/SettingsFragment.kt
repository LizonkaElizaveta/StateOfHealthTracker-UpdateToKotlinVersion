package stanevich.elizaveta.stateofhealthtracker.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = SettingsViewModelFactory(application)

        val settingsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding.settingsViewModel = settingsViewModel

        binding.lifecycleOwner = this

//        binding.apply {
//            buttonNotifications.setOnClickListener { view: View ->
//                Navigation.findNavController(view)
//                    .navigate(R.id.action_settingsFragment_to_notificationsFragment)
//            }
//            buttonPersonalDate.setOnClickListener { view: View ->
//                Navigation.findNavController(view)
//                    .navigate(R.id.action_settingsFragment_to_usersDataFragment)
//            }
////            buttonSendData.setOnClickListener{view:View ->
//                Navigation.findNavController(view).navigate()
//            }

//        }
        return binding.root
    }
}