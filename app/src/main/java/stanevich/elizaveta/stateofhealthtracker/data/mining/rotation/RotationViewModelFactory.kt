package stanevich.elizaveta.stateofhealthtracker.data.mining.rotation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.stateofhealthtracker.home.database.RotationDatabaseDao

class RotationViewModelFactory(
    private val dataSource: RotationDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RotationViewModel::class.java)) {
            return RotationViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}