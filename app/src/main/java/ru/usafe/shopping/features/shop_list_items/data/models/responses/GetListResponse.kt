package ru.usafe.shopping.features.shop_list_items.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.usafe.shopping.features.shop_list_items.data.models.ShopListItemDto

@Serializable
data class GetListResponse(
    @SerialName("success")
    val isSuccess: Boolean,

    @SerialName("item_list")
    val itemList: List<ShopListItemDto>
)