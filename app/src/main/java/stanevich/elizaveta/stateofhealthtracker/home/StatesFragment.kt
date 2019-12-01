package stanevich.elizaveta.stateofhealthtracker.home

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
import stanevich.elizaveta.stateofhealthtracker.dialogs.MedicationDialog
import stanevich.elizaveta.stateofhealthtracker.dialogs.ConfirmationDialog
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabase

class StatesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_states, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = StatesDatabase.getInstance(application).statesDatabaseDao

        val viewModelFactory = StatesViewModelFactory(
            dataSource,
            application
        )

        val statesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(StatesViewModel::class.java)

        binding.statesViewModel = statesViewModel

        binding.lifecycleOwner = this

        statesViewModel.showThanksDialogEvent.observe(this, Observer<Boolean> {
            if (it == true) {
                fragmentManager?.let { it1 -> ConfirmationDialog().show(it1, "ThanksDialog") }
                statesViewModel.doneShowingThanksDialog()
            }

        })

        statesViewModel.showMedDialogEvent.observe(this, Observer<Boolean> {
            if (it == true) {
                fragmentManager?.let { it1 ->
                    MedicationDialog(statesViewModel.updatedStateOfHealth).show(
                        it1,
                        "MedicationDialog"
                    )
                }

                statesViewModel.doneShowingMedDialog()
            }
        })

        return binding.root
    }

}