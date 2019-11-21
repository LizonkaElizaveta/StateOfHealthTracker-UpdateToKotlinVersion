package stanevich.elizaveta.stateofhealthtracker.profile.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.profile.database.Profile
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDao


class ProfileViewModel(
    private val database: ProfileDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var usersData = MutableLiveData<Profile?>()

    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()


//    fun loadUser(){
//        uiScope.launch {
//            usersData.value = getUserFromDatabase()
//        }
//    }

    init {
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            usersData.value = getUserFromDatabase()
            name.value = usersData.value?.name
            surname.value = usersData.value?.surname
            phone.value = usersData.value?.phone
            birthday.value = usersData.value?.birthday

            Log.d("mLog", "Insert ${usersData.value}")
        }
    }

    private suspend fun getUserFromDatabase(): Profile? {
        return withContext(Dispatchers.IO) {
            var user = database.findByUser()
            user ?: Profile()
        }

    }

    fun onUserNameTextChanged(name: CharSequence) {
        usersData.value!!.name = name.toString()
    }

    fun onUserSurnameTextChanged(surname: CharSequence) {
        usersData.value!!.surname = surname.toString()
    }

    fun onUserPhoneTextChanged(phone: CharSequence) {
        usersData.value!!.phone = phone.toString()
    }

    fun onUserBirthdayTextChanged(birthday: CharSequence) {
        usersData.value!!.birthday = birthday.toString()
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