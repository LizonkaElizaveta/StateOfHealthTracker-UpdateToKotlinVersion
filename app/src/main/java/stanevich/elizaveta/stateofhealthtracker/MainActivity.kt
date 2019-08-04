package stanevich.elizaveta.stateofhealthtracker

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityMainBinding
import stanevich.elizaveta.stateofhealthtracker.dialogs.ThanksConfirmationDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dialog: ThanksConfirmationDialog =  ThanksConfirmationDialog(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.apply {
            imageButtonExcellent.setOnClickListener { showDialogThanks() }
            imageButtonSatisfactorily.setOnClickListener { showDialogThanks() }
            imageButtonBad.setOnClickListener { showDialogThanks() }
            buttonDyskinesia.setOnClickListener { showDialogThanks() }
            buttonMedication.setOnClickListener { }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> toast()
        }
        return true
    }

    private fun toast(length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, R.string.toast_save_data, length).show()
    }


    private fun showDialogThanks() {
        return dialog.showDialog(
             getString(R.string.dialogHeadline_thanks),
            getString(R.string.dialogText_thanks)
        )
    }
}
