package stanevich.elizaveta.stateofhealthtracker.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentTestBinding
import stanevich.elizaveta.stateofhealthtracker.test.adapter.TestAdapter
import stanevich.elizaveta.stateofhealthtracker.test.database.TestDatabase

class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTestBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TestDatabase.getInstance(application).testDatabaseDao()

        val viewModelFactory = TestViewModelFactory(dataSource, application)

        val testViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TestViewModel::class.java)

        binding.lifecycleOwner = this

        binding.testViewModel = testViewModel


        binding.testList.adapter = TestAdapter(TestAdapter.OnClickListener {
            Toast.makeText(activity, "Work!", Toast.LENGTH_SHORT).show()
        })

        return binding.root
    }
}