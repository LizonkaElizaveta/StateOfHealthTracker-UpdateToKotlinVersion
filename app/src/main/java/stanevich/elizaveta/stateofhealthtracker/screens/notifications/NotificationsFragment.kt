package stanevich.elizaveta.stateofhealthtracker.screens.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationsBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.CategoryDialog

class NotificationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentNotificationsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notifications,
            container,
            false
        )


        val application = requireNotNull(this.activity).application

        val dataSource = NotificationsDatabase.getInstance(application).notificationsDatabaseDao


        val viewModelFactory = NotificationsViewModelFactory(dataSource, application)

        val notificationsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NotificationsViewModel::class.java)

        binding.notificationsViewModel = notificationsViewModel

        binding.lifecycleOwner = this

        val adapter = NotificationsAdapter()
        binding.notificationsList.adapter = adapter

        notificationsViewModel.notifications.observe(viewLifecycleOwner, Observer {
            it?.let {
                CategoryDialog().show(fragmentManager, "CategoryDialog")
            }
        })

        return binding.root
    }
}