package com.adden00.shopping_list.features.shop_list_items_screen.data.models.responses

import com.adden00.shopping_list.features.shop_list_items_screen.data.models.ShopListItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetListResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("item_list")
    val itemList: List<ShopListItemDto>
)