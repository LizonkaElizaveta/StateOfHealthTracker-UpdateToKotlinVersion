package stanevich.elizaveta.stateofhealthtracker.test.games.print.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.test.games.database.PrintTestDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest
import java.util.*

class PrintTestViewModel(application: Application, private val database: PrintTestDatabaseDao) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private var usersData = MutableLiveData<PrintTest?>()

    private var erased = 0
    private lateinit var userText : String

    private var startTimer: Long = 0
    private var stopTimer: Long = 0

    init {
        usersData.value = PrintTest()
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun savePrintData(origText : String) {
        if (stopTimer == 0L) {
            stopTimer = Calendar.getInstance().timeInMillis
            usersData.value!!.apply {
                time = stopTimer - startTimer
                date = Calendar.getInstance().timeInMillis
                originalText = origText
            }
        }
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.insert(usersData.value!!)
                usersData.postValue(usersData.value)
                Log.d("mLog", database.findAll().toString())
            }
        }

    }

    fun onPrintTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
        if (startTimer == 0L) {
            startTimer = Calendar.getInstance().timeInMillis
        }
        if (after - count < 0) {
            erased++
            usersData.value!!.erased = erased
        }
        userText = text.toString()
        usersData.value!!.userText = userText

    }


}