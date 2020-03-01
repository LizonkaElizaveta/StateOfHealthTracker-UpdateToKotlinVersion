package stanevich.elizaveta.stateofhealthtracker.test.games.voice.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestReadIntroBinding

class ReadIntroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestReadIntroBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_read_intro,
                container,
                false
            )

        binding.btnStart.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_readIntroFragment_to_readTestFragment)
        }

        return binding.root
    }
}