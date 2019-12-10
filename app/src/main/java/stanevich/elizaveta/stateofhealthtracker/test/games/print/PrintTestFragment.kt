package stanevich.elizaveta.stateofhealthtracker.test.games.print

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestPrintBinding

class PrintTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTestPrintBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_print, container, false)

        val application = requireNotNull(this.activity).application

        binding.lifecycleOwner = this


        return binding.root
    }
}