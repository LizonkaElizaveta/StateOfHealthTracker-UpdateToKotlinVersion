package stanevich.elizaveta.stateofhealthtracker.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestBinding
import stanevich.elizaveta.stateofhealthtracker.test.adapter.TestAdapter

class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        val application = requireNotNull(this.activity).application


        val viewModelFactory = TestViewModelFactory(application)

        val testViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TestViewModel::class.java)

        binding.lifecycleOwner = this

        binding.testViewModel = testViewModel


        binding.testList.adapter = TestAdapter {
            //TODO(ES): implement test selector
            when (it.name) {
                "tapping" -> Navigation.findNavController(binding.testList)
                    .navigate(R.id.action_nav_test_to_tappingIntroFragment)
                "draw" -> Navigation.findNavController(binding.testList)
                    .navigate(R.id.action_nav_test_to_drawingIntroFragment)
                "print" -> Navigation.findNavController(binding.testList)
                    .navigate(R.id.action_nav_test_to_printIntroFragment)
                "voice_emotional" -> Navigation.findNavController(binding.testList)
                    .navigate(R.id.action_nav_test_to_emotionalIntroFragment)
                else -> Toast.makeText(activity, "В разработке", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }
}