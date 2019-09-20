package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.database.StatesDatabase
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentStatesBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.MedicationDialog
import stanevich.elizaveta.stateofhealthtracker.dialogs.ThanksDialog

class StatesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_states, container, false)
        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application

        val dataSource = StatesDatabase.getInstance(application).statesDatabaseDao

        val viewModelFactory = StatesViewModelFactory(dataSource, application)

        val statesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(StatesViewModel::class.java)

        binding.statesViewModel = statesViewModel

        binding.lifecycleOwner = this

        statesViewModel.showThanksDialogEvent.observe(this, Observer<Boolean> {
            if (it == true) {
                fragmentManager?.let { it1 -> ThanksDialog().show(it1, "ThanksDialog") }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }


}