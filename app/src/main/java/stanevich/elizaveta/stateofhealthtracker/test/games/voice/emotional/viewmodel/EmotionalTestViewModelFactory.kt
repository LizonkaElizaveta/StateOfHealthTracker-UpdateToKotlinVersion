package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EmotionalTestViewModelFactory(
    private val application: Application,
    private val onFinish: (ampl: Array<Double>, path: String)->Unit
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmotionalTestViewModel::class.java)) {
            return EmotionalTestViewModel(
                application,
                onFinish
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}