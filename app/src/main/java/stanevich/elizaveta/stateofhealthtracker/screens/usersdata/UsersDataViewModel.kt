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

    fun onUserNameTextChanged(name: CharSequence) {
        usersData.value!!.usersFirstName = name.toString()
    }

    fun onUserSurnameTextChanged(surname: CharSequence) {
        usersData.value!!.userSurname = surname.toString()
    }

    fun onUserPhoneTextChanged(phone: CharSequence) {
        usersData.value!!.usersPhone = phone.toString()
    }

    fun onUserDocEmailTextChanged(email: CharSequence) {
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}