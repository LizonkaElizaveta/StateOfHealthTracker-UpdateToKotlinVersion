package stanevich.elizaveta.stateofhealthtracker.screens.usersdata

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.stateofhealthtracker.databases.dao.UsersDataDao

class UsersDataViewModelFactory (
    private val dataSource: UsersDataDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersDataViewModel::class.java)) {
            return UsersDataViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}