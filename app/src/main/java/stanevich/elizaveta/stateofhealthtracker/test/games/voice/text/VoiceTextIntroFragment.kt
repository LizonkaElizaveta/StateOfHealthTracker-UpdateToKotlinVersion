package stanevich.elizaveta.stateofhealthtracker.test.games.voice.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestPrintIntroBinding

class VoiceTextIntroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestPrintIntroBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_voice_text_intro,
                container,
                false
            )

        binding.btnStart.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_voiceTextIntroFragment_to_voiceTextTestFragment)
        }

        return binding.root
    }
}