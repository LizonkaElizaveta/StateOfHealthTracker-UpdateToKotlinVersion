package stanevich.elizaveta.stateofhealthtracker.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import stanevich.elizaveta.stateofhealthtracker.test.database.TestDatabase

class TestViewModel(application: Application) :
    AndroidViewModel(application) {

    init {
        val testDao = TestDatabase.getInstance(application, viewModelScope).testDatabaseDao()
    }


}