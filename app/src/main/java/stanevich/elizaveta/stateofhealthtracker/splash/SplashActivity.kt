package stanevich.elizaveta.stateofhealthtracker.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        splashViewModel.splashState.observe(this, Observer {
            when (it) {
                is SplashState.TutorialActivity -> {
                    goToTutorialActivity()
                }
            }
        })
    }

    private fun goToTutorialActivity() {
        finish()
        startActivity(Intent(this, SplashState.TutorialActivity::class.java))
    }
}