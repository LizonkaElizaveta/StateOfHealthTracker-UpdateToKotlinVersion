package stanevich.elizaveta.stateofhealthtracker.notification.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.databinding.ListItemNotificationsDayOfWeekBinding
import stanevich.elizaveta.stateofhealthtracker.notification.model.CheckBoxModel

class CheckBoxModelAdapter :
    androidx.recyclerview.widget.ListAdapter<CheckBoxModel, CheckBoxModelAdapter.CheckBoxModelViewHolder>(
        DiffCallback
    ) {

    class CheckBoxModelViewHolder(private var binding: ListItemNotificationsDayOfWeekBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(daysOfWeek: CheckBoxModel) {
            binding.daysOfWeek = daysOfWeek
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckBoxModelViewHolder {
        return CheckBoxModelViewHolder(
            ListItemNotificationsDayOfWeekBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }


    override fun onBindViewHolder(holder: CheckBoxModelViewHolder, position: Int) {
        val daysOfWeek = getItem(position)
        holder.bind(daysOfWeek)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<CheckBoxModel>() {
        override fun areItemsTheSame(oldItem: CheckBoxModel, newItem: CheckBoxModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CheckBoxModel, newItem: CheckBoxModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

}