package stanevich.elizaveta.stateofhealthtracker.test.games.drawing

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestDrawingFigureBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.TestResultDialog
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.random.Random

class DrawingTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTestDrawingFigureBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_test_drawing_figure,
                container,
                false
            )

        val application = requireNotNull(this.activity).application
        val figureList = listOf(
            R.drawable.test_drawing_figure_circle, R.drawable.test_drawing_figure_heart,
            R.drawable.test_drawing_figure_pentagon, R.drawable.test_drawing_figure_square,
            R.drawable.test_drawing_figure_star, R.drawable.test_drawing_figure_triangle
        )

        val figureShow =
            figureList[Random(Calendar.getInstance().timeInMillis).nextInt(figureList.size)]

        binding.imgFigure.setImageDrawable(application.getDrawable(figureShow))

        setupToolbar()



        binding.lifecycleOwner = this

        binding.btnSave.setOnClickListener {
            fragmentManager?.let { fm ->
                TestResultDialog {
                    NavHostFragment.findNavController(this@DrawingTestFragment)
                        .navigate(R.id.action_drawingTestFragment_to_nav_test)
                }.show(fm, "TestResultDialog")

                binding.finger.drawable?.let { drawing ->
                    ByteArrayOutputStream().use {
                        drawing.toBitmap()
                            .compress(Bitmap.CompressFormat.PNG, 100, it) //bm is the bitmap object
                        val b: ByteArray = it.toByteArray()
                        val base64Picture = Base64.encodeToString(b, Base64.DEFAULT)
                        Log.d("mLog", base64Picture.toString())
                        Log.d("mLog", b.toString())
                    }


                }

            }
        }
        return binding.root
    }


    private fun setupToolbar() {
        val toolbar = activity?.toolbar
        toolbar?.setBackgroundColor(Color.parseColor("#00000000"))
        toolbar?.title = ""
    }
}