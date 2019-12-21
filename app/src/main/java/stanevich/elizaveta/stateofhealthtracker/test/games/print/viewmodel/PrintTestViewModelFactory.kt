package stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.stateofhealthtracker.test.games.database.PrintTestDatabaseDao

class PrintTestViewModelFactory(
    private val application: Application,
    private val dataSource: PrintTestDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrintTestViewModel::class.java)) {
            return PrintTestViewModel(
                application,
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}