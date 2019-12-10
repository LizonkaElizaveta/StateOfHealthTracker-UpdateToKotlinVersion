package stanevich.elizaveta.stateofhealthtracker.test.games.tapping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestIntroBinding

class TappingIntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestIntroBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_intro, container, false)

        binding.btnStart.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_tappingIntroFragment_to_tappingTestFragment)
        }

        return binding.root
    }
}