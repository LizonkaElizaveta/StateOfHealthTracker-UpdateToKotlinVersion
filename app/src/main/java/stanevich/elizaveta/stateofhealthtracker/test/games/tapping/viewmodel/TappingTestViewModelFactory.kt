package stanevich.elizaveta.stateofhealthtracker.test.games.tapping.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TappingTestViewModelFactory(
    private val application: Application,
    private val onFinish: (taps: Int)->Unit
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TappingTestViewModel::class.java)) {
            return TappingTestViewModel(
                application,
                onFinish
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}