package stanevich.elizaveta.stateofhealthtracker.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.databinding.ListItemProfileBinding
import stanevich.elizaveta.stateofhealthtracker.profile.model.PopulateProfileData

class ProfileAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<PopulateProfileData, ProfileAdapter.ProfileViewHolder>(
        DiffCallback
    ) {

    class ProfileViewHolder(private var binding: ListItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profileData: PopulateProfileData) {
            binding.profileData = profileData
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        return ProfileViewHolder(ListItemProfileBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profileData = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(profileData)
        }
        holder.bind(profileData)
    }


    class OnClickListener(val clickListener: (profileData: PopulateProfileData) -> Unit) {
        fun onClick(profileData: PopulateProfileData) = clickListener(profileData)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<PopulateProfileData>() {
        override fun areItemsTheSame(
            oldItem: PopulateProfileData,
            newItem: PopulateProfileData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PopulateProfileData,
            newItem: PopulateProfileData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

}