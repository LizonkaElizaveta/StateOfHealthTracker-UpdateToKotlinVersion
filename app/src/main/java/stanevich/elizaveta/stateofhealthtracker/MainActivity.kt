package stanevich.elizaveta.stateofhealthtracker

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityMainBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.thanksconfirm.ThanksConfirmationDialogListener

class MainActivity : AppCompatActivity(), ThanksConfirmationDialogListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    override fun onDialogPositiveClick() {
        Log.d("mLog","HHHHHHHHH")
    }

    override fun onDialogNegativeClick() {
        Log.d("mLog","HHHHHHHHH")
    }
}
