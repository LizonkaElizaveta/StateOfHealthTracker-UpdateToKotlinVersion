package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.concurrent.timer


class EmotionalTestViewModel(application: Application, val onFinish: (taps: Int) -> Unit) :
    AndroidViewModel(application) {

    companion object{
        const val INITIAL_SECONDS = 0
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /*val time = MutableLiveData<String>(secondsToString(INITIAL_SECONDS))
*/
    /*private val taps = MutableLiveData(0)
*/
    var procent: Int = 0

    private var timer: Timer? = null

    private fun initTimer(): Timer {
        var seconds = INITIAL_SECONDS + 1
        return timer(period = 1000) {

            uiScope.run {
                procent = seconds * 100 / 30
            }

            if (seconds == 30) {
                cancel()/*
                onFinish(taps.value ?: 0 )*/
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