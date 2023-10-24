package com.adden00.shopping_list.features.cards.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adden00.shopping_list.core.utills.OnCardClickListener
import com.adden00.shopping_list.databinding.CardItemBinding
import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres

class CardsAdapter(private val listener: OnCardClickListener) :
    ListAdapter<CardModelPres, CardsAdapter.ItemHolder>(object :
        DiffUtil.ItemCallback<CardModelPres>() {
        override fun areItemsTheSame(oldItem: CardModelPres, newItem: CardModelPres): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CardModelPres, newItem: CardModelPres): Boolean =
            oldItem == newItem
    }) {
    inner class ItemHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun render(item: CardModelPres) {
            if (!item.isAdding)
                binding.card.setCardBackgroundColor(
                    Color.parseColor(
                        "#" + item.cardHex.replace(
                            "#",
                            ""
                        )
                    )
                )
            binding.tvCardCode.text = item.cardCode
            binding.tvCardName.text = item.cardName
            itemView.setOnClickListener {
                if (item.isAdding)
                    listener.onAddCard()
                else
                    listener.onClick(item)
            }
            binding.content.visibility = if (item.isAdding) View.GONE else View.VISIBLE
            binding.imAdd.visibility = if (item.isAdding) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }
}