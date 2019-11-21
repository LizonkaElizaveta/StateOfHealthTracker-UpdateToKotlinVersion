package stanevich.elizaveta.stateofhealthtracker.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.profile.ProfileActivity
import stanevich.elizaveta.stateofhealthtracker.tutorial.fragments.*


class TutorialActivity : AppIntro() {

//    private var prevStarted = "prevStartedTutorial"
//
//    override fun onResume() {
//        super.onResume()
//        val sharedPreferences =
//            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
//        if (!sharedPreferences.getBoolean(prevStarted, false)) {
//            val editor = sharedPreferences.edit()
//            editor.putBoolean(prevStarted, java.lang.Boolean.TRUE)
//            editor.apply()
//        } else {
//            startActivity(Intent(this, ProfileFirstStartActivity::class.java))
//            finish()
//
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainColor = ContextCompat.getColor(this, R.color.colorPrimary)

        addSlide(WelcomeTutorialFragment())
        addSlide(HomeTutorialFragment())
        addSlide(ProfileTutorialFragment())
        addSlide(TestTutorialFragment())
        addSlide(NotificationTutorialFragment())

        isProgressButtonEnabled = true
        showSkipButton(false)

        setColorDoneText(mainColor)

        setIndicatorColor(mainColor, mainColor)

        showSeparator(false)
        setNextArrowColor(mainColor)
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}