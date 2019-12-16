package stanevich.elizaveta.stateofhealthtracker


import android.app.Application
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
import stanevich.elizaveta.stateofhealthtracker.data.mining.location.LocationPermissionsActivity
import stanevich.elizaveta.stateofhealthtracker.data.mining.rotation.RotationViewModel
import stanevich.elizaveta.stateofhealthtracker.data.mining.rotation.RotationViewModelFactory
import stanevich.elizaveta.stateofhealthtracker.data.mining.service.DataMiningForegroundService
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityMainBinding
import stanevich.elizaveta.stateofhealthtracker.databinding.NavHeaderMainBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.DataMiningDialog
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabase
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModel
import stanevich.elizaveta.stateofhealthtracker.profile.viewModel.ProfileViewModelFactory


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

        val profileViewModel = getProfileViewModel(application)

        headerBind.lifecycleOwner = this

        headerBind.profileViewModel = profileViewModel


        DataMiningDialog({
            startActivity(Intent(this@MainActivity, LocationPermissionsActivity::class.java))
            DataMiningForegroundService.saveServiceEnabled(this@MainActivity, true)
        }, {
            DataMiningForegroundService.saveServiceEnabled(this@MainActivity, false)
        }).show(supportFragmentManager, "DataMiningDialog")

        getRotationViewModel(application)
    }

    private fun getRotationViewModel(application: Application): RotationViewModel {
        val dataSource = StatesDatabase.getInstance(application).rotationDatabaseDao
        val viewModelFactory = RotationViewModelFactory(dataSource, application)
        return ViewModelProviders.of(this, viewModelFactory).get(RotationViewModel::class.java)
    }

    private fun getProfileViewModel(application: Application): ProfileViewModel {
        val dataSource = ProfileDatabase.getInstance(application).profileDatabaseDao

        val viewModelFactory = ProfileViewModelFactory(dataSource, application)

        return ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
