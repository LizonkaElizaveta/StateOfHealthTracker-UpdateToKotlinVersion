package stanevich.elizaveta.stateofhealthtracker.test.games.voice.text.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VoiceTextTestViewModelFactory(
    private val application: Application,
    private val onFinish: (path: String) -> Unit
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoiceTextTestViewModel::class.java)) {
            return VoiceTextTestViewModel(
                application,
                onFinish
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}