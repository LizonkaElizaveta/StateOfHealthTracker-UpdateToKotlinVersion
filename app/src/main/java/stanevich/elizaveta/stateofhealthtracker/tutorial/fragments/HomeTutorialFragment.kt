package stanevich.elizaveta.stateofhealthtracker.tutorial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTutorialHomeBinding

class HomeTutorialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTutorialHomeBinding.inflate(inflater)

        return binding.root
    }
}