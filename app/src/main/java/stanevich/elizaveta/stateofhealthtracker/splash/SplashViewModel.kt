package stanevich.elizaveta.stateofhealthtracker.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _splashState = MutableLiveData<TutorialState>()

    val tutorialState: LiveData<TutorialState>
        get() = _splashState

    init {
        GlobalScope.launch {
            delay(200)
            _splashState.postValue(TutorialState.TutorialActivity())
        }
    }
}

sealed class TutorialState {
    class TutorialActivity : TutorialState()
}