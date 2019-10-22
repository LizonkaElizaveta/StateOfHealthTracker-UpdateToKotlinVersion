package stanevich.elizaveta.stateofhealthtracker.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stanevich.elizaveta.stateofhealthtracker.test.database.Test
import stanevich.elizaveta.stateofhealthtracker.test.database.TestDatabaseDao

class TestViewModel(private val databaseDao: TestDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _data = MutableLiveData<List<Test>>()

    val data: LiveData<List<Test>>
        get() = _data

    init {
        viewModelScope.launch {
            initialize()
        }
    }

    suspend fun initialize() {
        val tests = withContext(Dispatchers.IO) {
            return@withContext databaseDao.getAll()
        }
        this._data.value = tests
    }

}