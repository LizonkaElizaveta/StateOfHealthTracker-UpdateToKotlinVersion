package stanevich.elizaveta.stateofhealthtracker.tutorial

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.tutorial.fragments.*

class TutorialActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainColor = ContextCompat.getColor(this, R.color.colorPrimary)

        addSlide(WelcomeTutorialFragment())
        addSlide(HomeTutorialFragment())
        addSlide(ProfileTutorialFragment())
        addSlide(TestTutorialFragment())
        addSlide(NotificationTutorialFragment())
        addSlide(NavigationTutorialFragment())

        isProgressButtonEnabled = true
        showSkipButton(false)

        setColorDoneText(mainColor)

        setIndicatorColor(mainColor, mainColor)

        showSeparator(false)
        setNextArrowColor(mainColor)

    }
}