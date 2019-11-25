package stanevich.elizaveta.stateofhealthtracker.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentProfileBinding
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModel
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModelFactory

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)

        val application = requireNotNull(this.activity).application

        val dataSource = ProfileDatabase.getInstance(application).profileDatabaseDao

        val viewModelFactory =
            ProfileViewModelFactory(dataSource, application)

        val profileViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        binding.lifecycleOwner = this

        binding.profileViewModel = profileViewModel


        binding.btnSave.setOnClickListener { view: View ->
            profileViewModel.saveUserData()
            Navigation.findNavController(view)
                .navigate(R.id.action_nav_profile_to_nav_home)
        }


        return binding.root
    }
}