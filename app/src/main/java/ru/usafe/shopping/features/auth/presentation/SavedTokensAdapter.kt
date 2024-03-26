package ru.usafe.shopping.features.auth.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.usafe.shopping.databinding.SavedTokenItemBinding

class SavedTokensAdapter(private val onTokenClick: (String) -> Unit) :
    ListAdapter<String, SavedTokensAdapter.ItemHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }) {
    inner class ItemHolder(private val binding: SavedTokenItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(token: String) {
            binding.tvToken.text = token
            binding.tvToken.setOnClickListener {
                onTokenClick(token)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            SavedTokenItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }
}