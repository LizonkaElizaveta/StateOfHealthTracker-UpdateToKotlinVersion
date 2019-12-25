package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.AudioRecording
import java.util.*
import kotlin.concurrent.timer


class EmotionalTestViewModel(
    application: Application,
    context: Context?,
    val onFinish: (ampl: Array<Double>, path: String) -> Unit
) : AndroidViewModel(application) {

    companion object{
        const val INITIAL_SECONDS = 0
        const val MAX_SECONDS = 30
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val valur_progressBar = MutableLiveData<Int>(0)
    var max_valur_progressBar = MAX_SECONDS

    private var ampl: Array<Double> = arrayOf(0.6,0.3)
    private var path = "Recording"

    private var timer: Timer? = null
    private var audioRecord: AudioRecording = AudioRecording(context)
    private fun initTimer(): Timer {
        var seconds = INITIAL_SECONDS + 1
        return timer(period = 1000) {

            uiScope.run {
                valur_progressBar.postValue(seconds)
            }

            if (seconds == MAX_SECONDS) {
                cancel()
                stop()
            }

            ++seconds
        }

    }

    fun incTaps() {
        if (timer == null) {
            timer = initTimer()
            audioRecord.startRecording()
        }
        else {
            timer!!.cancel()
            stop()
        }
    }

    private fun stop(){
        audioRecord.stopRecording()
        path = audioRecord.getFullNameAudioFile()
        onFinish(ampl, path)
    }
}