package stanevich.elizaveta.stateofhealthtracker.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stanevich.elizaveta.stateofhealthtracker.test.database.Test
import stanevich.elizaveta.stateofhealthtracker.test.database.TestDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.test.database.populateData

class TestViewModel(private val databaseDao: TestDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _data = MutableLiveData<List<Test>>()

    val data: LiveData<List<Test>>
        get() = _data

    init {
        val tests = populateData()
        viewModelScope.launch {
            initialize(tests)
        }
    }

    private fun initialize(tests: List<Test>) {
        this._data.value = tests
    }

}