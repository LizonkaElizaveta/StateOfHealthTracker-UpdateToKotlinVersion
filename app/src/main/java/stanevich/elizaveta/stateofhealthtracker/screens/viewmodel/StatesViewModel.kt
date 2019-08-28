package stanevich.elizaveta.stateofhealthtracker.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao

class StatesViewModel(val database: StatesDatabaseDao,
                      application: Application) : AndroidViewModel(application){
}