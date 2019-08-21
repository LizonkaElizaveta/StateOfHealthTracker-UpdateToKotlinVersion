package stanevich.elizaveta.stateofhealthtracker.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentStatesBinding

class StatesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentStatesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_states, container, false)
        binding.buttonDyskinesia.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_statesFragment_to_settingsFragment)
        }
        return binding.root
    }
}