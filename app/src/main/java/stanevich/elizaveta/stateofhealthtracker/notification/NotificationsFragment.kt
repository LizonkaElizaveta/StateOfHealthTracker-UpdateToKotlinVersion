package stanevich.elizaveta.stateofhealthtracker.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationsBinding
import stanevich.elizaveta.stateofhealthtracker.notification.adapter.NotificationsAdapter
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotificationsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notifications,
            container,
            false
        )


        val application = requireNotNull(this.activity).application

        val dataSource = NotificationsDatabase.getInstance(application).notificationsDatabaseDao


        val viewModelFactory =
            NotificationsViewModelFactory(
                dataSource,
                application
            )

        val notificationsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NotificationsViewModel::class.java)

        binding.notificationsViewModel = notificationsViewModel

        binding.lifecycleOwner = this

        val adapter =
            NotificationsAdapter()
        binding.notificationsList.adapter = adapter


        binding.fab.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_nav_notifications_to_notificationSettingsFragment)
        }
//        notificationsViewModel.showNotDialogEvent.observe(viewLifecycleOwner, Observer {
//            if (it == true) {
//                fragmentManager?.let { it1 ->
//                    CategoryDialog(
//                        notificationsViewModel.tonightNotification
//                    ) { notificationsViewModel.startTracking() }.show(it1, "CategoryDialog")
//                }
//
//                notificationsViewModel.doneShowingNotDialog()
//
//            }
//        })


        notificationsViewModel.notifications.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}