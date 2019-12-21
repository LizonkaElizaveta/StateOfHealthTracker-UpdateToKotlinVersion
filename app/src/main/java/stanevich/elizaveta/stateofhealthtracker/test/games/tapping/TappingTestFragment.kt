package stanevich.elizaveta.stateofhealthtracker.test.games.tapping

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
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestTappingBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.TappingTestResultDialog
import stanevich.elizaveta.stateofhealthtracker.test.games.database.TestingDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.viewmodel.TappingTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.viewmodel.TappingTestViewModelFactory


class TappingTestFragment : Fragment() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupToolbar()

        val binding: FragmentTestTappingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_tapping, container, false)

        val tappingTestViewModel = getTappingTestViewModel()

        binding.lifecycleOwner = this

        binding.tappingTest = tappingTestViewModel

        navigation = NavHostFragment.findNavController(this)

        binding.target.setOnClickListener {
            tappingTestViewModel.incTaps()
        }

        return binding.root
    }

    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }

    private fun getTappingTestViewModel(): TappingTestViewModel {
        val application = requireNotNull(this.activity).application

        val tappingTestDatabase = TestingDatabase.getInstance(application).tappingTestDatabaseDao

        val viewModelFactory =
            TappingTestViewModelFactory(application, fragmentManager) { leftCount, rightCount ->

            uiScope.launch {
                withContext(Dispatchers.IO){
                    tappingTestDatabase.insert(
                        TappingTest(
                            leftCount = leftCount,
                            rightCount = rightCount
                        )
                    )

                    Log.d("mTag", tappingTestDatabase.findAll().toString())
                }
            }

            fragmentManager?.let {
                val dialog = TappingTestResultDialog(leftCount, rightCount) {
                    uiScope.launch{
                        navigation.navigate(R.id.action_tappingTestFragment_to_nav_test)
                    }
                }
                dialog.show(it, "TappingResultDialog")
            }
        }

        return ViewModelProviders.of(this, viewModelFactory).get(TappingTestViewModel::class.java)
    }
}