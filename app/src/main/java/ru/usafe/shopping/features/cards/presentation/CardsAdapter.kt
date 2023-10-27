package ru.usafe.shopping.features.cards.presentation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import ru.usafe.shopping.R
import ru.usafe.shopping.core.utills.OnCardClickListener
import ru.usafe.shopping.databinding.CardItemBinding
import ru.usafe.shopping.features.cards.presentation.models.CardModelPres

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
                try {
                    binding.card.setCardBackgroundColor(
                        Color.parseColor(
                            "#" + item.cardHex.replace(
                                "#",
                                ""
                            )
                        )
                    )

                } catch (e: IllegalArgumentException) {
                    Log.d("card", "incorrect color! color: ${item.cardHex}")
                }
            binding.tvCardCode.text = if (item.cardCode.length > 7) "${
                item.cardCode.substring(
                    0,
                    7
                )
            }..." else item.cardCode

            binding.tvCardName.text = item.cardName


            binding.imColor.setOnClickListener {
                openColorPicker(item)
            }
            binding.imRemove.setOnClickListener {
                listener.onLongClick(item)
            }
            itemView.setOnClickListener {
                if (item.isAdding)
                    listener.onAddCard()
                else
                    listener.onClick(item)
            }
            binding.content.visibility = if (item.isAdding) View.GONE else View.VISIBLE

        }

        private fun openColorPicker(item: CardModelPres) {
            ColorPickerDialogBuilder
                .with(binding.root.context)
                .setTitle("Choose color")
                .initialColor(binding.root.context.getColor(R.color.primary))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener {
                }
                .setPositiveButton(
                    "ok"
                ) { _, selectedColor, _ ->
                    val color = Integer.toHexString(selectedColor).uppercase()
                    try {
                        binding.card.setCardBackgroundColor(
                            Color.parseColor(
                                "#$color"
                            )
                        )
                        listener.onUpdateCard(item.copy(cardHex = color))
                    } catch (e: IllegalArgumentException) {
                        Log.d("card", "incorrect color! color: ${item.cardHex}")
                    }
                }
                .setNegativeButton(
                    "cancel"
                ) { _, _ -> }
                .build()
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(getItem(position))
    }
}