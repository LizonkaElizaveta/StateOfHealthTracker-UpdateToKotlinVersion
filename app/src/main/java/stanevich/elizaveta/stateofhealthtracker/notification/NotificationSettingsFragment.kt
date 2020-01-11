package stanevich.elizaveta.stateofhealthtracker.notification

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationSettingsBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.DatePickerFragment
import stanevich.elizaveta.stateofhealthtracker.dialogs.TimePickerFragment
import stanevich.elizaveta.stateofhealthtracker.notification.adapter.CheckBoxModelAdapter
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.notification.dialogs.CategoryDialog
import stanevich.elizaveta.stateofhealthtracker.notification.viewModel.NotificationsSettingsViewModel
import stanevich.elizaveta.stateofhealthtracker.notification.viewModel.NotificationsSettingsViewModelFactory
import stanevich.elizaveta.stateofhealthtracker.utils.getDetailDate
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

        val application = requireNotNull(this.activity).application

        val dataSource = NotificationsDatabase.getInstance(application).notificationsDatabaseDao

        binding.lifecycleOwner = this

        binding.apply {
            tvDay.text = getDetailDate(Calendar.getInstance().timeInMillis)
            tvTime.text = getTime(Calendar.getInstance().timeInMillis)
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
                        binding.tvDay.text = getDetailDate(calendar.timeInMillis)
                    }).show(it, "DatePicker")
                }

                notificationsSettingsViewModel.doneShowingNotDialogDate()
            }
        })

        notificationsSettingsViewModel.showNotDialogTime.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val tpd =
                    TimePickerFragment(TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val calendar = TimePickerFragment.getCalendarTime(hourOfDay, minute)
                        binding.tvTime.text = (getTime(calendar.timeInMillis))
                    })
                fragmentManager?.let { fmt ->
                    tpd.show(fmt, "TimePicker")
                }

                notificationsSettingsViewModel.doneShowingNotDialogTime()
            }
        })
        val checkboxList = binding.checkboxList as RecyclerView

        binding.checkboxList.adapter = CheckBoxModelAdapter {
            var isChecked = true
            for (i in 0 until it.itemCount) {
                if (!(checkboxList[i].findViewById(R.id.ch_days_of_week) as CheckBox).isChecked) {
                    isChecked = false
                    break
                }
            }

            binding.btnSwitch.isChecked = isChecked
        }

        binding.btnSwitch.setOnClickListener { buttonView ->
            val checkBoxAdapter = checkboxList.adapter as CheckBoxModelAdapter
            for (i in 0 until checkBoxAdapter.itemCount) {
                (checkboxList[i].findViewById(R.id.ch_days_of_week) as CheckBox).isChecked =
                    (buttonView as Switch).isChecked
            }
        }

        binding.apply {
            btnSave.setOnClickListener {
                val category = tvCategory.text.toString()
                val date = tvDay.text.toString()
                val time = tvTime.text.toString()
                val repeat = BooleanArray(7)
                for (i in 0 until (checkboxList.adapter?.itemCount ?: 7)) {
                    repeat[i] =
                        (checkboxList[i].findViewById(R.id.ch_days_of_week) as CheckBox).isChecked
                }

                notificationsSettingsViewModel.onStartTracking(category, date, time, repeat)
                Navigation.findNavController(it)
                    .navigate(R.id.action_notificationSettingsFragment_to_nav_notifications)
            }
        }


        return binding.root

    }


}