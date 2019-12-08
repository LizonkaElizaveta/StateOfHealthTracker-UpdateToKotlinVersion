package stanevich.elizaveta.stateofhealthtracker.notification.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_notifications_day_of_week.view.*
import stanevich.elizaveta.stateofhealthtracker.databinding.ListItemNotificationsDayOfWeekBinding
import stanevich.elizaveta.stateofhealthtracker.notification.model.CheckBoxModel

class CheckBoxModelAdapter(private val onClickListener: (checkBox: CheckBoxModelAdapter)->Unit) :
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
        holder.itemView.ch_days_of_week.setOnClickListener { onClickListener(this) }
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

    public override fun getItem(position: Int): CheckBoxModel {
        return super.getItem(position)
    }

}