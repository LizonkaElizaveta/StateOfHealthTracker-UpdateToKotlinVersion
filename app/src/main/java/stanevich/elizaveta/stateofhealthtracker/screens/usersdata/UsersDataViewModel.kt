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


//
//    private var _etUser = MutableLiveData<Boolean>(false)
//
//    val triggerEvent : LiveData<Boolean>
//    get() = _etUser
//


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
            var user = database.getLastUser()
            user
        }

    }

    fun onStartTracking(firstName: String, lastName: String, phone: Int, mail: String) {
        uiScope.launch {
            val newUser = UsersData(
                usersFirstName = firstName,
                userLastName = lastName,
                usersPhone = phone,
                usersEmail = mail
            )
            insert(newUser)
            usersData.value = getUserFromDatabase()
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
//        usersData.value = database.findByUser()
//        usersData.value!!.usersFirstName = name.toString()
//        Log.d("mLog", usersData.value.toString())
    }

    fun onUserSurnameTextChanged(surname: CharSequence): String {
        Log.d("mLog", surname.toString())
        return surname.toString()
    }

    fun onUserPhoneTextChanged(phone: CharSequence): Int? {
        Log.d("mLog", phone.toString())
        return Integer.parseInt(phone as String)
    }

    fun onUserDocEmailTextChanged(email: CharSequence): String {
        Log.d("mLog", email.toString())
        return email.toString()
    }

}