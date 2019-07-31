package stanevich.elizaveta.stateofhealthtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        imageButton_excellent.setOnClickListener { toast("Work!") }
        imageButton_satisfactorily.setOnClickListener { toast("Work!") }
        imageButton_bad.setOnClickListener { toast("Work!") }
        button_dyskinesia.setOnClickListener{toast("Work!")}
        button_medication.setOnClickListener{toast("Work!")}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            Toast.makeText(this,"Work!",Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this,message,length).show()
    }
}
