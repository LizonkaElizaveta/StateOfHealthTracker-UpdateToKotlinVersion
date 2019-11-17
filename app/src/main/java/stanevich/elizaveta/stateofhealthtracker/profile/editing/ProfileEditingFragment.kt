package stanevich.elizaveta.stateofhealthtracker.profile.editing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentProfileEditingBinding

class ProfileEditingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileEditingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_editing, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

//        val dataSource = ProfileDatabase.getInstance(application).statesDatabaseDao
//
//        val viewModelFactory = ProfileViewModelFactory(dataSource, application)
//
//        val profileViewModel =
//            ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
//
//        binding.profileViewModel = profileViewModel
//
//        binding.profileList.adapter = ProfileAdapter(ProfileAdapter.OnClickListener {
//            Toast.makeText(activity, "Work!", Toast.LENGTH_SHORT).show()
//        })

        return binding.root
    }
}