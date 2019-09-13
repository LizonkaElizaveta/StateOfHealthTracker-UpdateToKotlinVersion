package stanevich.elizaveta.stateofhealthtracker.screens.usersdata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.database.UsersDataDatabase
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentUsersDataBinding

class UsersDataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUsersDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_users_data, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = UsersDataDatabase.getInstance(application).statesDatabaseDao

        val viewModelFactory = UsersDataViewModelFactory(dataSource, application)

        val usersViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(UsersDataViewModel::class.java)

        binding.usersViewModel = usersViewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}