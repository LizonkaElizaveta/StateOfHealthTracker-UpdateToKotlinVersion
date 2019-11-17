package stanevich.elizaveta.stateofhealthtracker.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentProfileBinding
import stanevich.elizaveta.stateofhealthtracker.profile.adapter.ProfileAdapter
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.profileViewModel = profileViewModel

//        val application = requireNotNull(this.activity).application
//
//        val dataSource = ProfileDatabase.getInstance(application).statesDatabaseDao
//
//        val viewModelFactory = ProfileViewModelFactory(dataSource, application)
//
//        val profileViewModel =
//            ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
//
//

//        binding.profileList.adapter = ProfileAdapter(ProfileAdapter.OnClickListener {
//            profileViewModel.displayPropertyDetails(it)
//        })
//
//        profileViewModel.navigateToSelectedProperty.observe(this, Observer {
//            if (null != it) {
//                this.findNavController().navigate(ProfileFragmentDirections.actionShowDetail(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                profileViewModel.displayPropertyDetailsComplete()
//            }
//        })

        return binding.root
    }
}