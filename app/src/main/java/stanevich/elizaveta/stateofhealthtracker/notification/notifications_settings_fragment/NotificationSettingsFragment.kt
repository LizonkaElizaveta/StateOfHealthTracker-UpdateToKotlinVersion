package stanevich.elizaveta.stateofhealthtracker.notification.notifications_settings_fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import stanevich.elizaveta.stateofhealthtracker.dialogs.DatePickerFragment
import stanevich.elizaveta.stateofhealthtracker.dialogs.TimePickerFragment
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.notification.dialogs.CategoryDialogD
import stanevich.elizaveta.stateofhealthtracker.utils.getFullDate
import stanevich.elizaveta.stateofhealthtracker.utils.getTime

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

        notificationsSettingsViewModel.showNotDialogCategory.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                fragmentManager?.let { it1 ->
                    CategoryDialogD(
                        notificationsSettingsViewModel.tonightNotification,
                        tvCategory
                    ).show(
                        it1,
                        "CategoryDialogD"
                    )
                }

                notificationsSettingsViewModel.doneShowingNotDialogCategory()
            }
        })

        notificationsSettingsViewModel.showNotDialogDate.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                fragmentManager?.let { it ->
                    DatePickerFragment(DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val calendar =
                            DatePickerFragment.getCalendarDate(year, monthOfYear, dayOfMonth)
                        binding.textDay.text = getFullDate(calendar.timeInMillis)
                    }).show(it, "DatePicker")
                }

                notificationsSettingsViewModel.doneShowingNotDialogDate()
            }
        })

        notificationsSettingsViewModel.showNotDialogTime.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                fragmentManager?.let { it ->
                    TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val calendar = TimePickerFragment.getCalendarTime(hourOfDay, minute)
                        binding.textTime.text = (getTime(calendar.timeInMillis))
                    }).show(it, "TimePicker")
                }

                notificationsSettingsViewModel.doneShowingNotDialogTime()
            }
        })

        return binding.root
    }


}