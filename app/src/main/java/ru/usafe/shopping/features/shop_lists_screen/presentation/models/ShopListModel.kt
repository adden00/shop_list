package ru.usafe.shopping.features.shop_lists_screen.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ShopListModel(
    val id: Int,
    val name: String,
    val created: String
): Parcelable