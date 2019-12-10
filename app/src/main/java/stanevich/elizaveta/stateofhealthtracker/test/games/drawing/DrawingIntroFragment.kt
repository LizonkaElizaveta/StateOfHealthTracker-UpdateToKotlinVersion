package stanevich.elizaveta.stateofhealthtracker.test.games.drawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestDrawingIntroBinding
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestTappingIntroBinding

class DrawingIntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestDrawingIntroBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_drawing_intro,
                container,
                false
            )

        binding.btnStart.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_drawingIntroFragment_to_drawingTestFragment)
        }

        return binding.root
    }
}