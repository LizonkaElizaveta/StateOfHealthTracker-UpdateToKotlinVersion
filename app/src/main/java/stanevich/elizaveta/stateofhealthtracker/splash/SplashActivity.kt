package stanevich.elizaveta.stateofhealthtracker.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.tutorial.TutorialActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        splashViewModel.tutorialState.observe(this, Observer {
            when (it) {
                is TutorialState.TutorialActivity -> {
                    goToTutorialActivity()
                }
            }
        })
    }

    private fun goToTutorialActivity() {
        finish()
        startActivity(Intent(this, TutorialActivity::class.java))
    }
}