package stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.test.games.database.PrintTestDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest

class PrintTestViewModel(application: Application, private val database: PrintTestDatabaseDao) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private var usersData = MutableLiveData<PrintTest?>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onUserNameTextChanged(userText: CharSequence) {
        usersData.value!!.userText = userText.toString()
    }


    fun saveUserData() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                usersData.postValue(usersData.value)
                Log.d("mLog", usersData.value.toString())
            }
        }
    }


}