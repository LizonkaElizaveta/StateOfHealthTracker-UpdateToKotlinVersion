package stanevich.elizaveta.stateofhealthtracker.notification

import android.app.Application
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databinding.FragmentNotificationsBinding
import stanevich.elizaveta.stateofhealthtracker.notification.adapter.NotificationsAdapter
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.notification.viewModel.NotificationsViewModel
import stanevich.elizaveta.stateofhealthtracker.notification.viewModel.NotificationsViewModelFactory


class NotificationsFragment : Fragment() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotificationsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notifications,
            container,
            false
        )

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = NotificationsDatabase.getInstance(application).notificationsDatabaseDao

        val viewModelFactory = NotificationsViewModelFactory(dataSource, application)

        val notificationsViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(NotificationsViewModel::class.java)


        binding.notificationsViewModel = notificationsViewModel

        val adapter = NotificationsAdapter()
        binding.notificationsList.adapter = adapter


        binding.fab.setOnClickListener { view: View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_nav_notifications_to_notificationSettingsFragment)
        }
        notificationsViewModel.notifications.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        recyclerSwipe(binding.notificationsList, application, notificationsViewModel)

        return binding.root
    }


    private fun recyclerSwipe(
        recyclerView: RecyclerView,
        application: Application,
        notificationsViewModel: NotificationsViewModel
    ) {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    removeItem(
                        notificationsViewModel,
                        recyclerView.adapter?.getItemId(viewHolder.adapterPosition) ?: 0
                    )
                }

                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        application, c, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive
                    )
                        .addBackgroundColor(
                            ContextCompat.getColor(
                                application,
                                R.color.colorBackgroundDelete
                            )
                        )
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate()

                    super.onChildDraw(
                        c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                    )
                }
            }).attachToRecyclerView(recyclerView)


    }

    fun removeItem(
        notificationsViewModel: NotificationsViewModel,
        notificationId: Long
    ) {
        uiScope.launch {
            notificationsViewModel.delete(notificationId)
        }
    }
}