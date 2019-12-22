package stanevich.elizaveta.stateofhealthtracker.test.games.tapping.viewmodel

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import stanevich.elizaveta.stateofhealthtracker.dialogs.TappingTestChangeHandDialog
import java.util.*
import kotlin.concurrent.timer


class TappingTestViewModel(
    application: Application,
    private val fragmentManager: FragmentManager?,
    private val onFinish: (leftCount: Int, rightCount: Int) -> Unit
) : AndroidViewModel(application) {

    companion object {
        const val INITIAL_SECONDS = 10
    }

    internal enum class STATE { START, LEFT, RIGHT }

    private var state: STATE = STATE.START

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val time = MutableLiveData<String>(secondsToString(INITIAL_SECONDS))

    private val taps = MutableLiveData(0)

    private var leftCount = 0
    private var rightCount = 0


    private var timer: Timer? = null

    private fun initTimer(): Timer {
        when (state) {
            STATE.START -> {
                state = STATE.LEFT
            }
            STATE.LEFT -> {
                state = STATE.RIGHT
            }
            STATE.RIGHT -> {
            }
        }

        var seconds = INITIAL_SECONDS - 1
        return timer(period = 1000) {

            uiScope.run {
                time.postValue(secondsToString(seconds))
            }

            if (seconds == 0) {
                cancel()
                if (state == STATE.RIGHT) {
                    rightCount = taps.value ?: 0
                    onFinish(leftCount, rightCount)
                } else {
                    fragmentManager?.let {
                        leftCount = taps.value ?: 0
                        taps.postValue(0)
                        TappingTestChangeHandDialog {

                            seconds = INITIAL_SECONDS - 1
                            uiScope.run {
                                time.postValue(secondsToString(seconds))
                            }
                            timer = null
                        }.show(it, "DialogChangeHand")
                    }

                }
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