package stanevich.elizaveta.stateofhealthtracker.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater)
//
//        binding.lifecycleOwner = this
//
//        binding.profileViewModel = profileViewModel

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