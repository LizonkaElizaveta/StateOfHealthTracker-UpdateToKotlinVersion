package stanevich.elizaveta.stateofhealthtracker.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _splashState = MutableLiveData<SplashState>()

    val splashState: LiveData<SplashState>
        get() = _splashState

    init {
        GlobalScope.launch {
            delay(500)
            _splashState.postValue(SplashState.TutorialActivity())
        }
    }
}

sealed class SplashState {
    class TutorialActivity : SplashState()
}