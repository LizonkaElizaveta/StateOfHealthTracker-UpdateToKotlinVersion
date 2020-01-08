package stanevich.elizaveta.stateofhealthtracker.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.databinding.ListItemNotificationsBinding
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications


class NotificationsAdapter : ListAdapter<Notifications,
        NotificationsAdapter.ViewHolder>(NotificationsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).notificatiionsId?:0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemNotificationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notifications) {
            binding.notification = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNotificationsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

        }
    }

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class NotificationsDiffCallback : DiffUtil.ItemCallback<Notifications>() {
        override fun areItemsTheSame(oldItem: Notifications, newItem: Notifications): Boolean {
            return oldItem.notificatiionsId == newItem.notificatiionsId
        }

        override fun areContentsTheSame(oldItem: Notifications, newItem: Notifications): Boolean {
            return oldItem == newItem
        }
    }
}