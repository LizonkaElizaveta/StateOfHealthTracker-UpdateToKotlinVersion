package stanevich.elizaveta.stateofhealthtracker.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private val testViewModel: TestViewModel by lazy {
        ViewModelProviders.of(this).get(TestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        binding.testViewModel = testViewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}