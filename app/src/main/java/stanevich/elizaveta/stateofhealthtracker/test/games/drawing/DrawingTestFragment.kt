package stanevich.elizaveta.stateofhealthtracker.test.games.drawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestDrawingFigureBinding

class DrawingTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTestDrawingFigureBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test_drawing_figure, container, false)

        val application = requireNotNull(this.activity).application

        binding.lifecycleOwner = this


        return binding.root
    }
}