package stanevich.elizaveta.stateofhealthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.databinding.DataBindingUtil
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
     private var dialog: ThanksConfirmationDialog = ThanksConfirmationDialog()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.apply {
            imageButtonExcellent.setOnClickListener { toast() }
            imageButtonSatisfactorily.setOnClickListener { toast() }
            imageButtonBad.setOnClickListener { toast() }
            buttonDyskinesia.setOnClickListener { toast() }
            buttonMedication.setOnClickListener { dialog.showDialog(this@MainActivity,getString(R.string.dialogHeadline_thanks),
                getString(R.string.dialogText_thanks)) }
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
}
