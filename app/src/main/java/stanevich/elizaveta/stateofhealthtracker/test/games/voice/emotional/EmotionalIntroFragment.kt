package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestVoiceEmotionIntroBinding

class EmotionalIntroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestVoiceEmotionIntroBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_voice_emotion_intro,
                container,
                false
            )

        binding.btnStart.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_emotionalIntroFragment_to_emotionalTestFragment2)
        }

        return binding.root
    }
}