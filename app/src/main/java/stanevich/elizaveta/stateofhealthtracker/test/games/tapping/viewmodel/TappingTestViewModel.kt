package stanevich.elizaveta.stateofhealthtracker.test.games.tapping.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.concurrent.timer


class TappingTestViewModel(application: Application, val onFinish: (taps: Int) -> Unit) :
    AndroidViewModel(application) {

    companion object{
        const val INITIAL_SECONDS = 10
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val time = MutableLiveData<String>(secondsToString(INITIAL_SECONDS))

    private val taps = MutableLiveData(0)

    private var timer: Timer? = null

    private fun initTimer(): Timer {
        var seconds = INITIAL_SECONDS - 1
        return timer(period = 1000) {

            uiScope.run {
                time.postValue(secondsToString(seconds))
            }

            if (seconds == 0) {
                cancel()
                onFinish(taps.value ?: 0)
            }

            --seconds
        }

    }

    fun incTaps() {
        if (timer == null) {
            timer = initTimer()
        }
        taps.value = taps.value?.plus(1)
    }

    private fun secondsToString(pTime: Int): String? {
        return String.format("%02d:%02d", pTime / 60, pTime % 60)
    }
}