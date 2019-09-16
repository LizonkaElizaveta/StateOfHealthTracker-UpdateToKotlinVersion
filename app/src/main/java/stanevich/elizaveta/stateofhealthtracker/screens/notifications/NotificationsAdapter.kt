package stanevich.elizaveta.stateofhealthtracker.screens.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.databases.entity.Notifications

class NotificationsAdapter : ListAdapter<Notifications,
        NotificationsAdapter.ViewHolder>(NotificationsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val notificationDate: TextView = itemView.findViewById(R.id.tvNotificationDate)
        private val category: TextView = itemView.findViewById(R.id.tvCategory)

        fun bind(item: Notifications) {
            notificationDate.text = item.notificationsTime.toString()
            category.text = item.notificationsText
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_notifications, parent, false)
                return ViewHolder(view)
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