package stanevich.elizaveta.stateofhealthtracker.screens.usersdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.UsersDataDao

class UsersDataViewModel(
    private val database: UsersDataDao,
    application: Application
) : AndroidViewModel(application) {


    override fun onCleared() {
        super.onCleared()
    }
}