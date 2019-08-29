package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao

class StatesViewModel(val database: StatesDatabaseDao,
                      application: Application) : AndroidViewModel(application){
}