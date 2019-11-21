package stanevich.elizaveta.stateofhealthtracker.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.MainActivity
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityProfileBinding
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModel
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private var prevStarted = "prevProfileActivity"

    override fun onResume() {
        super.onResume()
        val sharedPreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean(prevStarted, false)) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(prevStarted, java.lang.Boolean.TRUE)
            editor.apply()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile)

        val application = requireNotNull(this).application

        val dataSource = ProfileDatabase.getInstance(application).profileDatabaseDao

        val viewModelFactory =
            ProfileViewModelFactory(dataSource, application)

        val profileViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        binding.lifecycleOwner = this

        binding.profileViewModel = profileViewModel

        binding.btnSave.setOnClickListener {
            profileViewModel.saveUserData()
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}