package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestVoiceEmotionBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.ConfirmationSaveDataDialog
import stanevich.elizaveta.stateofhealthtracker.test.games.database.TestingDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model.EmotionalTest
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model.AudioRecording
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.EmotionalTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.EmotionalTestViewModelFactory


class EmotionalTestFragment : Fragment() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupToolbar()

        val binding: FragmentTestVoiceEmotionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_voice_emotion, container, false)

        val emotionalTestViewModel = getEmotonalTestViewModel()

        binding.lifecycleOwner = this

        binding.emotionTest = emotionalTestViewModel

        navigation = NavHostFragment.findNavController(this)

        binding.btnVoice.setOnClickListener {
            emotionalTestViewModel.incTaps()
        }

        return binding.root
    }

    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }

    private fun getEmotonalTestViewModel(): EmotionalTestViewModel {
        val application = requireNotNull(this.activity).application

        val emotionalTestDatabase = TestingDatabase.getInstance(application).emotionalTestDatabaseDao

        val viewModelFactory = EmotionalTestViewModelFactory(application) { ampl, path ->

            uiScope.launch {
                withContext(Dispatchers.IO){
                    emotionalTestDatabase.insert(
                        EmotionalTest(
                            amplitudes = ampl,
                            path = path
                        )
                    )
                }
            } 

            fragmentManager?.let { ConfirmationSaveDataDialog().show(it, "ThanksDialog") }
        }

        return ViewModelProviders.of(this, viewModelFactory).get(EmotionalTestViewModel::class.java)
    }
}