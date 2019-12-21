package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestVoiceEmotionBinding
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.AudioRecording
import java.util.*


class EmotionalTestFragment : Fragment() {
    private var recording: AudioRecording? = null

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupToolbar()

        val binding: FragmentTestVoiceEmotionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_voice_emotion, container, false)

        binding.lifecycleOwner = this

        navigation = NavHostFragment.findNavController(this)

        recording = AudioRecording(this.context)

        var i = 0
        binding.btnVoice.setOnClickListener {
            val timer = object: CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    binding.progress.setProgress(i * 100 / (30000 / 1000))
                }

                override fun onFinish() {
                    binding.progress.setProgress(100)
                }
            }
            if (recording!!.isRecording){
                timer.start()
            }
            else {
                timer.cancel()
            }
        }

        return binding.root
    }



    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }
}