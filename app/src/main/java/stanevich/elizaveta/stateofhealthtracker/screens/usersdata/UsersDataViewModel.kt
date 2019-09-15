package stanevich.elizaveta.stateofhealthtracker.screens.usersdata

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.UsersDataDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.UsersData


class UsersDataViewModel(
    private val database: UsersDataDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var usersData = MutableLiveData<UsersData?>()


    init {
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            usersData.value = getUserFromDatabase()
            Log.d("mLog", "Insert ${usersData.value}")
        }
    }

    private suspend fun getUserFromDatabase(): UsersData? {
        return withContext(Dispatchers.IO) {
            var user = database.findByUser()
            user ?: UsersData()
        }

    }
    private suspend fun insert(user: UsersData) {
        withContext(Dispatchers.IO) {
            database.insert(user)

        }

    }

    private suspend fun update(state: UsersData) {
        withContext(Dispatchers.IO) {
            database.update(state)
        }
    }

    fun onUserNameTextChanged(name: CharSequence) {
        Log.d("mLog", name.toString())
        usersData.value!!.usersFirstName = name.toString()
    }

    fun onUserSurnameTextChanged(surname: CharSequence) {
        Log.d("mLog", surname.toString())
        usersData.value!!.userSurname = surname.toString()
    }

    fun onUserPhoneTextChanged(phone: CharSequence) {
        Log.d("mLog", phone.toString())
        usersData.value!!.usersPhone = phone.toString()
    }

    fun onUserDocEmailTextChanged(email: CharSequence) {
        Log.d("mLog", email.toString())
        usersData.value!!.usersEmail = email.toString()
    }

    fun saveUserData() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.upsert(usersData.value!!)
                Log.d("mLog", usersData.value.toString())
            }
        }
    }

}