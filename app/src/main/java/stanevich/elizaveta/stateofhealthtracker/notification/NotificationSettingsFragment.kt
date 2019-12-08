package stanevich.elizaveta.stateofhealthtracker.notification

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationSettingsBinding
import stanevich.elizaveta.stateofhealthtracker.databinding.ListItemNotificationsDayOfWeekBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.DatePickerFragment
import stanevich.elizaveta.stateofhealthtracker.dialogs.TimePickerFragment
import stanevich.elizaveta.stateofhealthtracker.notification.adapter.CheckBoxModelAdapter
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.notification.dialogs.CategoryDialog
import stanevich.elizaveta.stateofhealthtracker.notification.viewModel.NotificationsSettingsViewModel
import stanevich.elizaveta.stateofhealthtracker.notification.viewModel.NotificationsSettingsViewModelFactory
import stanevich.elizaveta.stateofhealthtracker.utils.getFullDate
import stanevich.elizaveta.stateofhealthtracker.utils.getTime
import java.util.*

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

        val bindingCheckBox: ListItemNotificationsDayOfWeekBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_item_notifications_day_of_week,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = NotificationsDatabase.getInstance(application).notificationsDatabaseDao

        binding.lifecycleOwner = this

        binding.apply {
            textDay.text = getFullDate(Calendar.getInstance().timeInMillis)
            textTime.text = getTime(Calendar.getInstance().timeInMillis)
        }

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
        bindingCheckBox.daysViewModel = notificationsSettingsViewModel

        notificationsSettingsViewModel.showNotDialogCategory.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                fragmentManager?.let { it1 ->
                    CategoryDialog(
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

        val adapter = CheckBoxModelAdapter()
        binding.checkboxList.adapter = adapter

        notificationsSettingsViewModel.switchState.observe(viewLifecycleOwner, Observer {
            val checkboxList = binding.checkboxList as RecyclerView
            val checkBoxAdapter = checkboxList.adapter as CheckBoxModelAdapter
            for (i in 0 until  checkBoxAdapter.itemCount){
//                checkBoxAdapter.getItem(i).isChecked = it == true
                (checkboxList[i].findViewById(R.id.ch_days_of_week) as CheckBox).isChecked = it == true
            }
        })

        return binding.root
    }


}