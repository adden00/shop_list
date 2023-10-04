package com.adden00.testtaskunisafe.features.shop_lists_screen.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adden00.testtaskunisafe.databinding.ShopListsItemBinding
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.models.ShopListModel

class ShopListsAdapter(private val listener: OnClickListener): ListAdapter<ShopListModel, ShopListsAdapter.ItemHolder>(object : DiffUtil.ItemCallback<ShopListModel>() {
    override fun areItemsTheSame(oldItem: ShopListModel, newItem: ShopListModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShopListModel, newItem: ShopListModel): Boolean = oldItem == newItem
}) {
    inner class ItemHolder(private val binding: ShopListsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun render(item: ShopListModel) {
            binding.textView.text = item.name
            itemView.setOnClickListener{
                listener.onClick(item)
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
    interface OnClickListener {
        fun onClick(item: ShopListModel)
        fun onLongClick(item: ShopListModel)
    }
}