package stanevich.elizaveta.stateofhealthtracker.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.stateofhealthtracker.databinding.ListItemTestBinding
import stanevich.elizaveta.stateofhealthtracker.test.model.Test

class TestAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Test, TestAdapter.TestViewHolder>(DiffCallback) {

    class TestViewHolder(private var binding: ListItemTestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(test: Test) {
            binding.test = test
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestViewHolder {
        return TestViewHolder(ListItemTestBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val test = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(test)
        }
        holder.bind(test)
    }


    class OnClickListener(val clickListener: (test: Test) -> Unit) {
        fun onClick(test: Test) = clickListener(test)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Test>() {
        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem.id == newItem.id
        }
    }

}