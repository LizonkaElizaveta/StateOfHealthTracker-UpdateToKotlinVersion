package stanevich.elizaveta.stateofhealthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.databinding.DataBindingUtil
import stanevich.elizaveta.stateofhealthtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.apply {
            imageButtonExcellent.setOnClickListener { toast() }
            imageButtonSatisfactorily.setOnClickListener { toast() }
            imageButtonBad.setOnClickListener { toast() }
            buttonDyskinesia.setOnClickListener { toast() }
            buttonMedication.setOnClickListener { toast() }
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
