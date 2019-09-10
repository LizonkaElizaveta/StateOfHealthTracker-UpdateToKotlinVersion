package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao

class StatesViewModelFactory(
    private val dataSource: StatesDatabaseDao,
    private val application: Application,
    private val lifecycleOwner: LifecycleOwner
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatesViewModel::class.java)) {
            return StatesViewModel(
                dataSource,
                application,
                lifecycleOwner
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}