package stanevich.elizaveta.stateofhealthtracker.test.games.voice.text

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import okhttp3.Dispatcher
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestPrintBinding
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestVoiceTextBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.ConfirmationSaveDataDialog
import stanevich.elizaveta.stateofhealthtracker.dialogs.TestResultDialog
import stanevich.elizaveta.stateofhealthtracker.test.games.database.TestingDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel.PrintTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel.PrintTestViewModelFactory
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel.EmotionalTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.RecordPermissionRequire
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.RecordPermissionsActivity
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.text.model.VoiceTextTest
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.text.viewmodel.VoiceTextTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.text.viewmodel.VoiceTextTestViewModelFactory
import java.util.*
import kotlin.random.Random


class VoiceTextTestFragment : Fragment() {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var navigation: NavController

    private lateinit var permissionRequire: RecordPermissionRequire

    private lateinit var voiceTextTestViewModel: VoiceTextTestViewModel

    private lateinit var originalText: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupToolbar()

        val binding: FragmentTestVoiceTextBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_voice_text,
                container,
                false
            )

        voiceTextTestViewModel = getVoiceTextTestViewModel()

        binding.lifecycleOwner = this

        binding.voiceTextTest = voiceTextTestViewModel

        navigation = NavHostFragment.findNavController(this)

        permissionRequire = RecordPermissionRequire(this.activity!!, fragmentManager!!)

        val textList: List<String> = listOf(
            getString(R.string.list_poems_one),
            getString(R.string.list_poems_two),
            getString(R.string.list_poems_three),
            getString(R.string.list_poems_four),
            getString(R.string.list_poems_five),
            getString(R.string.list_poems_six)
        )

        originalText =
            textList[Random(Calendar.getInstance().timeInMillis).nextInt(textList.size)]
        binding.text.text = originalText

        binding.btnVoice.setOnClickListener {
            onClickBtnVoice()
        }

        return binding.root
    }

    private fun onClickBtnVoice() {
        if (permissionRequire.checkAllPermissions()) {
            voiceTextTestViewModel.voiceBtnClick()
        } else {
            startActivity(Intent(activity, RecordPermissionsActivity::class.java))
        }
    }

    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }

    private fun getVoiceTextTestViewModel():VoiceTextTestViewModel{
        val application = requireNotNull(this.activity).application

        val voiceTextTestDatabase =
            TestingDatabase.getInstance(application).voiceTextTestDatabaseDao

        val viewModelFactory = VoiceTextTestViewModelFactory(application) {path ->

            uiScope.launch {
                withContext(Dispatchers.IO) {
                    voiceTextTestDatabase.insert(
                        VoiceTextTest(
                            originalText = originalText,
                            path = path
                        )
                    )
                }
            }

            fragmentManager?.let {
                uiScope.launch {
                    navigation.navigate(R.id.action_voiceTextTestFragment_to_nav_test)
                }
                ConfirmationSaveDataDialog().show(it, "ThanksDialog")
            }
        }

        return ViewModelProviders.of(this, viewModelFactory).get(VoiceTextTestViewModel::class.java)
    }
}