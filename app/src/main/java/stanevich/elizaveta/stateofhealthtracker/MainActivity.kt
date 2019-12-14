package stanevich.elizaveta.stateofhealthtracker


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityMainBinding
import stanevich.elizaveta.stateofhealthtracker.databinding.NavHeaderMainBinding
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModel
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModelFactory
import stanevich.elizaveta.stateofhealthtracker.service.location.LocationPermissionsActivity


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.bringToFront()
        setSupportActionBar(toolbar)

        val navController = this.findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_notifications,
                R.id.nav_profile, R.id.nav_settings, R.id.nav_test
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        val headerBind = DataBindingUtil.inflate<NavHeaderMainBinding>(
            layoutInflater,
            R.layout.nav_header_main,
            binding.navView,
            false
        )
        binding.navView.addHeaderView(headerBind.root)

        val application = requireNotNull(this).application

        val dataSource = ProfileDatabase.getInstance(application).profileDatabaseDao

        val viewModelFactory =
            ProfileViewModelFactory(dataSource, application)

        val profileViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        headerBind.lifecycleOwner = this

        headerBind.profileViewModel = profileViewModel

        startActivity(Intent(this, LocationPermissionsActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
