package stanevich.elizaveta.stateofhealthtracker.notification.notifications_settings_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationSettingsBinding
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.notification.dialogs.CategoryDialogD

class NotificationSettingsFragment : Fragment() {

    private lateinit var tvCategory: TextView

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
        val application = requireNotNull(this.activity).application

        val dataSource = NotificationsDatabase.getInstance(application).notificationsDatabaseDao

        binding.lifecycleOwner = this

        val viewModelFactory =
            NotificationsSettingsViewModelFactory(
                dataSource,
                application
            )

        val notificationsSettingsViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(NotificationsSettingsViewModel::class.java)

        tvCategory = binding.tvCategory

        binding.notificationsSettingsViewModel = notificationsSettingsViewModel

        notificationsSettingsViewModel.showNotDialogEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                fragmentManager?.let { it1 ->
                    CategoryDialogD(
                        notificationsSettingsViewModel.tonightNotification,
                        tvCategory
                    ) { notificationsSettingsViewModel.startTracking() }.show(
                        it1,
                        "CategoryDialogD"
                    )
                }

                notificationsSettingsViewModel.doneShowingNotDialog()
            }
        })



        return binding.root
    }


}