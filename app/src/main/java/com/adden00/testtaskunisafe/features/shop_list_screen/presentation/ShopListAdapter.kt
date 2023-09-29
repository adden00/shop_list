package com.adden00.testtaskunisafe.features.shop_list_screen.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adden00.testtaskunisafe.databinding.ShopListItemBinding
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.models.ShopListModel

class ShopListAdapter(private val onClickCallback: (ShopListModel)->Unit): ListAdapter<ShopListModel, ShopListAdapter.ItemHolder>(object : DiffUtil.ItemCallback<ShopListModel>() {
    override fun areItemsTheSame(oldItem: ShopListModel, newItem: ShopListModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShopListModel, newItem: ShopListModel): Boolean = oldItem == newItem
}) {
    inner class ItemHolder(private val binding: ShopListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun render(item: ShopListModel) {
            binding.textView.text = item.name
            itemView.setOnClickListener{
                onClickCallback(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ShopListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }
}