package stanevich.elizaveta.stateofhealthtracker.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentStatesBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.ConfirmationSaveResultDialog
import stanevich.elizaveta.stateofhealthtracker.dialogs.DateAndTimeDialog
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabase
import stanevich.elizaveta.stateofhealthtracker.view.tracking.ViewTracker

class StatesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_states, container, false)

        val application = requireNotNull(this.activity).application

        val statesViewModel = initStatesViewModel(application)

        binding.statesViewModel = statesViewModel

        binding.lifecycleOwner = this

        startTrackViews(binding, statesViewModel)

        return binding.root
    }

    private fun startTrackViews(
        binding: FragmentStatesBinding,
        statesViewModel: StatesViewModel
    ) {
        binding.apply {
            trackingView.consumer =
                { timestamp: Long, clickDistanceFromCenter: Double, closestEvents: List<ViewTracker.ClosestTouchEvent> ->
                    run {
                        statesViewModel.saveMissClick(
                            timestamp,
                            clickDistanceFromCenter,
                            closestEvents
                        )
                    }
                }


            listOf(
                imageButtonExcellent,
                imageButtonSatisfactorily,
                imageButtonBad,
                buttonDyskinesia,
                buttonMedication
            ).forEach {
                trackingView.addTrackedView(it)
            }
        }
    }

    private fun initStatesViewModel(
        application: Application
    ): StatesViewModel {
        val statesDatabaseDao = StatesDatabase.getInstance(application).statesDatabaseDao
        val missClickDatabaseDao = StatesDatabase.getInstance(application).missClickDatabaseDao

        val viewModelFactory = StatesViewModelFactory(
            statesDatabaseDao,
            missClickDatabaseDao,
            application
        )

        val statesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(StatesViewModel::class.java)


        statesViewModel.showThanksDialogEvent.observe(this, Observer<Boolean> {
            if (it == true) {
                fragmentManager?.let { fm ->
                    ConfirmationSaveResultDialog().show(
                        fm,
                        "ThanksDialog"
                    )
                }
                statesViewModel.doneShowingThanksDialog()
            }
        })

        statesViewModel.showMedDialogEvent.observe(this, Observer<Boolean> {
            if (it == true) {
                fragmentManager?.let { fm ->
                    DateAndTimeDialog(statesViewModel.updatedStateOfHealth).show(
                        fm,
                        "MedicationDialog"
                    )
                }

                statesViewModel.doneShowingMedDialog()
            }
        })
        return statesViewModel
    }
}