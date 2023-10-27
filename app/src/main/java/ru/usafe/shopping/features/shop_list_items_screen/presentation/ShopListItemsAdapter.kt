package ru.usafe.shopping.features.shop_list_items_screen.presentation

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.usafe.shopping.R
import ru.usafe.shopping.core.utills.OnClickListener
import ru.usafe.shopping.databinding.ShopListItemItemBinding
import ru.usafe.shopping.features.shop_list_items_screen.presentation.models.ShopListItemModel

class ShopListItemsAdapter(private val listener: OnClickListener<ShopListItemModel>): ListAdapter<ShopListItemModel, ShopListItemsAdapter.ItemHolder>(object : DiffUtil.ItemCallback<ShopListItemModel>() {
    override fun areItemsTheSame(oldItem: ShopListItemModel, newItem: ShopListItemModel): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShopListItemModel, newItem: ShopListItemModel): Boolean = oldItem == newItem
}) {
    inner class ItemHolder(private val binding: ShopListItemItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun render(item: ShopListItemModel) {

            binding.checkbox.isChecked = item.isCrossed
            binding.imRemove.setOnClickListener {
                listener.onLongClick(item)
            }
            itemView.setOnClickListener {
                listener.onClick(item)
            }
            itemView.setOnLongClickListener {
                listener.onLongClick(item)
                true
            }
            binding.tvName.text = item.name
            if (item.isCrossed) {
                binding.tvName.paintFlags = binding.tvName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.card.setCardBackgroundColor(binding.root.context.getColor(R.color.light_pink))
            } else {
                binding.tvName.paintFlags =
                    binding.tvName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.card.setCardBackgroundColor(binding.root.context.getColor(R.color.light_green))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ShopListItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }
}