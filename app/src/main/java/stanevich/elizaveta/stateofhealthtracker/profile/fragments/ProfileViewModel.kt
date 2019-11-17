package stanevich.elizaveta.stateofhealthtracker.profile.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDao
import stanevich.elizaveta.stateofhealthtracker.profile.model.PopulateProfileData
import stanevich.elizaveta.stateofhealthtracker.profile.model.populateData


class ProfileViewModel(
    private val database: ProfileDao,
    application: Application
) : AndroidViewModel(application) {
    private val _data = MutableLiveData<List<PopulateProfileData>>()

    val data: LiveData<List<PopulateProfileData>>
        get() = _data

    init {
        val populateData = populateData()
        viewModelScope.launch {
            initialize(populateData)
        }
    }

    private fun initialize(tests: List<PopulateProfileData>) {
        this._data.value = tests
    }


//    private var viewModelJob = Job()
//
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//
//    private var usersData = MutableLiveData<Profile?>()
//
//
//    init {
//        initializeUser()
//    }
//
//    private fun initializeUser() {
//        uiScope.launch {
//            usersData.value = getUserFromDatabase()
//            Log.d("mLog", "Insert ${usersData.value}")
//        }
//    }
//
//    private suspend fun getUserFromDatabase(): Profile? {
//        return withContext(Dispatchers.IO) {
//            var user = database.findByUser()
//            user ?: Profile()
//        }
//
//    }
//
//    fun onUserNameTextChanged(name: CharSequence) {
//        usersData.value!!.usersFirstName = name.toString()
//    }
//
//    fun onUserSurnameTextChanged(surname: CharSequence) {
//        usersData.value!!.userSurname = surname.toString()
//    }
//
//    fun onUserPhoneTextChanged(phone: CharSequence) {
//        usersData.value!!.usersPhone = phone.toString()
//    }
//
//    fun onUserDocEmailTextChanged(email: CharSequence) {
//        usersData.value!!.usersEmail = email.toString()
//    }
//
//    fun saveUserData() {
//        uiScope.launch {
//            withContext(Dispatchers.IO) {
//                database.upsert(usersData.value!!)
//                Log.d("mLog", usersData.value.toString())
//            }
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

}