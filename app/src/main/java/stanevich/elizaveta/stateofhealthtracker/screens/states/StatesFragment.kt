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
import stanevich.elizaveta.stateofhealthtracker.dialogs.ThanksConfirmationDialog

class StatesFragment : Fragment(){


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

        var unlockedT = false
        var unlockedM = false

        statesViewModel.triggerThanksEvent.observe(this, Observer<Boolean> {
            if(unlockedT){
                ThanksConfirmationDialog(
                    statesViewModel.updatedStateOfHealth,resources.getString(R.string.dialogText_thanks)
                ).show(fragmentManager, "ThanksDialog")
            } else{
                unlockedT = true
            }
    })

        statesViewModel.triggerMedicationEvent.observe(this, Observer<Boolean> {
            if(unlockedM){
                MedicationDialog(statesViewModel.updatedStateOfHealth).show(fragmentManager, "MedicationDialog")

            } else{
                unlockedM = true
            }
        })



        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item!!,
            view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }


}