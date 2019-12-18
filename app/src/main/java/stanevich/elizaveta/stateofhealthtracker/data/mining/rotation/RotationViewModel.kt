package stanevich.elizaveta.stateofhealthtracker.data.mining.rotation

import android.app.Application
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import stanevich.elizaveta.stateofhealthtracker.home.database.RotationDatabaseDao

class RotationViewModel(
    private val dataSource: RotationDatabaseDao,
    application: Application
) : ViewModel() {

    private val rotationDetector =
        RotationDetector(application, RotationDetector.REPEAT_IN_MILLISECONDS)

    init {
        initRotationDataMining()
    }

    private fun initRotationDataMining() {
        rotationDetector.getOrientation {
            CoroutineScope(Dispatchers.IO).launch {
                dataSource.insert(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        rotationDetector.clear()
    }
}