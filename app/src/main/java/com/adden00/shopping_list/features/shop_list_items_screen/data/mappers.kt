package com.adden00.shopping_list.features.shop_list_items_screen.data

import com.adden00.shopping_list.features.shop_list_items_screen.data.models.ShopListItemDto
import com.adden00.shopping_list.features.shop_list_items_screen.domain.models.ShopListItemModelDomain

fun ShopListItemDto.toDomain(): ShopListItemModelDomain =
    ShopListItemModelDomain(
        created = this.created,
        name = this.name,
        id = this.id,
        isCrossed = this.isCrossed
    )