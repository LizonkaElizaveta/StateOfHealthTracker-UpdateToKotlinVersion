package stanevich.elizaveta.stateofhealthtracker.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentSettingsBinding
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentUsersDataBinding

class UsersDataFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentUsersDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_users_data, container, false)
        return binding.root
    }
}