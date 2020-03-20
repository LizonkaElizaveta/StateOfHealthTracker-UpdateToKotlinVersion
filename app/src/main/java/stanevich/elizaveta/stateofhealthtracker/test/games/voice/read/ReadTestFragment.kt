package stanevich.elizaveta.stateofhealthtracker.test.games.voice.read

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestReadBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.ConfirmationSaveDataDialog
import stanevich.elizaveta.stateofhealthtracker.dialogs.StateDialog
import stanevich.elizaveta.stateofhealthtracker.test.games.database.TestingDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.model.ReadTest
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.viewmodel.ReadTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.viewmodel.ReadTestViewModelFactory
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.RecordPermissionRequire
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.RecordPermissionsActivity
import java.util.*
import kotlin.random.Random


class ReadTestFragment : Fragment() {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var navigation: NavController

    private lateinit var permissionRequire: RecordPermissionRequire

    private lateinit var readTestViewModel: ReadTestViewModel

    private lateinit var originalText: String
    private var emotion = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupToolbar()

        val binding: FragmentTestReadBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_read,
                container,
                false
            )

        emotion.value = ""

        readTestViewModel = getReadTestViewModel()

        binding.lifecycleOwner = this

        binding.readTest = readTestViewModel

        navigation = NavHostFragment.findNavController(this)

        permissionRequire = RecordPermissionRequire(this.activity!!, fragmentManager!!)

        val textList = resources.getStringArray(R.array.texts)

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
            if (emotion.value == "") {
                fragmentManager?.let {
                    emotion.value = ""
                    StateDialog(emotion).show(it, "StateDialog")
                }
            } else {
                readTestViewModel.voiceBtnClick()
            }
        } else {
            startActivity(Intent(activity, RecordPermissionsActivity::class.java))
        }
    }

    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }

    private fun getReadTestViewModel(): ReadTestViewModel {
        val application = requireNotNull(this.activity).application

        val readTestDatabase =
            TestingDatabase.getInstance(application).readTestDatabaseDao

        val viewModelFactory = ReadTestViewModelFactory(application) { path ->

            uiScope.launch {
                withContext(Dispatchers.IO) {
                    readTestDatabase.insert(
                        ReadTest(
                            originalText = originalText,
                            path = path,
                            emotion = emotion.value!!
                        )
                    )
                }
            }

            fragmentManager?.let {
                uiScope.launch {
                    navigation.navigate(R.id.action_readTestFragment_to_nav_test)
                }
                ConfirmationSaveDataDialog().show(it, "ThanksDialog")
            }
        }

        return ViewModelProviders.of(this, viewModelFactory).get(ReadTestViewModel::class.java)
    }
}