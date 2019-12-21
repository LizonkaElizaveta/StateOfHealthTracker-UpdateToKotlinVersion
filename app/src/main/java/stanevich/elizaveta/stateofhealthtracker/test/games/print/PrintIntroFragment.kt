package stanevich.elizaveta.stateofhealthtracker.test.games.print

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestPrintIntroBinding

class PrintIntroFragment : Fragment() {

//
//    private var prevStarted = "prevStartedPrintTest"
//
//    override fun onResume() {
//        super.onResume()
//        val sharedPreferences =
//            this.context!!.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
//        if (!sharedPreferences.getBoolean(prevStarted, false)) {
//            val editor = sharedPreferences.edit()
//            editor.putBoolean(prevStarted, java.lang.Boolean.TRUE)
//            editor.apply()
//        } else {
//            startActivity(Intent(context, PrintTestFragment::class.java))
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestPrintIntroBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_print_intro,
                container,
                false
            )

        binding.btnStart.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_printIntroFragment_to_printTestFragment)
        }

        return binding.root
    }
}