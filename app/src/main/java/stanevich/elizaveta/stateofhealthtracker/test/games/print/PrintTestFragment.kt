package stanevich.elizaveta.stateofhealthtracker.test.games.print

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestPrintBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.TestResultDialog
import stanevich.elizaveta.stateofhealthtracker.test.games.database.TestingDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel.PrintTestViewModel
import stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel.PrintTestViewModelFactory

class PrintTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTestPrintBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_print, container, false)

        setupToolbar()

        binding.lifecycleOwner = this

        binding.btnSave.setOnClickListener {
            fragmentManager?.let { fm ->
                TestResultDialog {
                    NavHostFragment.findNavController(this@PrintTestFragment)
                        .navigate(R.id.action_printTestFragment_to_nav_test)
                }.show(fm, "TestResultDialog")
            }
        }
        return binding.root
    }


    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }

    private fun getPrintTestViewModel(): PrintTestViewModel {
        val application = requireNotNull(this.activity).application

        val printTestDatabase = TestingDatabase.getInstance(application).printTestDatabaseDao

        val viewModelFactory = PrintTestViewModelFactory(application)

        return ViewModelProviders.of(this, viewModelFactory).get(PrintTestViewModel::class.java)
    }
}