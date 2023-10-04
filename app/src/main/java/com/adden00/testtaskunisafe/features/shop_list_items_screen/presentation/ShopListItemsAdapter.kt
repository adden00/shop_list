package com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adden00.testtaskunisafe.databinding.ShopListItemItemBinding
import com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.models.ShopListItemModel

class ShopListItemsAdapter: ListAdapter<ShopListItemModel, ShopListItemsAdapter.ItemHolder>(object : DiffUtil.ItemCallback<ShopListItemModel>() {
    override fun areItemsTheSame(oldItem: ShopListItemModel, newItem: ShopListItemModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShopListItemModel, newItem: ShopListItemModel): Boolean = oldItem == newItem
}) {
    inner class ItemHolder(private val binding: ShopListItemItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun render(item: ShopListItemModel) {
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    binding.tvName.paintFlags = binding.tvName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else
                    binding.tvName.paintFlags = binding.tvName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            binding.tvName.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ShopListItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }
}