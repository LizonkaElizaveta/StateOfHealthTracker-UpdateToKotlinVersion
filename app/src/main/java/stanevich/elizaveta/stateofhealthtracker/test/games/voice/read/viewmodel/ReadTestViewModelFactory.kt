package stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReadTestViewModelFactory(
    private val application: Application,
    private val onFinish: (path: String) -> Unit
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadTestViewModel::class.java)) {
            return ReadTestViewModel(
                application,
                onFinish
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}