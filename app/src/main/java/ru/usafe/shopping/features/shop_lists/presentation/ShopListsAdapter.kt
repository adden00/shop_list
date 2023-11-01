package ru.usafe.shopping.features.shop_lists.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.usafe.shopping.core.utills.OnClickListener
import ru.usafe.shopping.databinding.ShopListsItemBinding
import ru.usafe.shopping.features.shop_lists.presentation.models.ShopListModel

class ShopListsAdapter(private val listener: OnClickListener<ShopListModel>): ListAdapter<ShopListModel, ShopListsAdapter.ItemHolder>(object : DiffUtil.ItemCallback<ShopListModel>() {
    override fun areItemsTheSame(oldItem: ShopListModel, newItem: ShopListModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShopListModel, newItem: ShopListModel): Boolean = oldItem == newItem
}) {
    inner class ItemHolder(private val binding: ShopListsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun render(item: ShopListModel) {
            binding.textView.text = item.name
            itemView.setOnClickListener {
                listener.onClick(item)
            }
            binding.imRemove.setOnClickListener {
                listener.onLongClick(item)
            }
            itemView.setOnLongClickListener {
                listener.onLongClick(item)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ShopListsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }

}