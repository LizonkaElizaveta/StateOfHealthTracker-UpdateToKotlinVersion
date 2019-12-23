package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.concurrent.timer


class EmotionalTestViewModel(
    application: Application,
    val onFinish: (ampl: Array<Double>, path: String) -> Unit
) : AndroidViewModel(application) {

    companion object{
        const val INITIAL_SECONDS = 0
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var procent: Int = 0

    private var ampl: Array<Double> = arrayOf(0.6,0.3)
    private var path = "Recording"

    private var timer: Timer? = null

    private fun initTimer(): Timer {
        var seconds = INITIAL_SECONDS + 1
        return timer(period = 1000) {

            uiScope.run {
                procent = seconds * 100 / 30
            }

            if (seconds == 30) {
                cancel()

                //здесь запомнить все сохраненные данные
                onFinish(ampl, path)
            }

            ++seconds
        }

    }

    fun incTaps() {
        if (timer == null) {
            timer = initTimer()
        }
    }
}