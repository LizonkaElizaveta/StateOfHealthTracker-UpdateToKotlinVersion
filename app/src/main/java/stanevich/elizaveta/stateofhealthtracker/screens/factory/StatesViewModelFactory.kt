package stanevich.elizaveta.stateofhealthtracker.screens.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.screens.viewmodel.StatesViewModel

class StatesViewModelFactory(
    private val dataSource: StatesDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatesViewModel::class.java)) {
            return StatesViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}