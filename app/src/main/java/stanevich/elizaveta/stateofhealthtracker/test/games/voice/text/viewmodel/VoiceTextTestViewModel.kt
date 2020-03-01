package stanevich.elizaveta.stateofhealthtracker.test.games.voice.text.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.AudioRecording
import java.util.*
import kotlin.concurrent.timer

class VoiceTextTestViewModel(
    application: Application,
    val onFinish: (path: String) -> Unit
) : AndroidViewModel(application) {

    companion object {
        const val INITIAL_SECONDS = 0
        const val MAX_SECONDS = 45
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val valueProgressBar = MutableLiveData(0)
    val maxValueProgressBar = MAX_SECONDS

    private var timer: Timer? = null

    private lateinit var audioRecord: AudioRecording


    private fun initTimer():Timer {
        var seconds = INITIAL_SECONDS + 1
        return timer(period = 1000) {

            uiScope.run {
                valueProgressBar.postValue(seconds)
            }

            if (seconds == MAX_SECONDS) {
                cancel()
                stopRecording()
            }

            ++seconds
        }
    }

    fun voiceBtnClick() {
        if (timer == null) {
            timer = initTimer()
            audioRecord = AudioRecording()
            audioRecord.startRecording()
        } else {
            timer!!.cancel()
            stopRecording()
        }
    }

    private fun stopRecording() {
        audioRecord.stopRecording()

        val path = audioRecord.getFullNameAudioFile()

        onFinish(path)
    }

}