package ru.usafe.shopping.features.shop_list_items_screen.data

import ru.usafe.shopping.features.shop_list_items_screen.data.models.ShopListItemDto
import ru.usafe.shopping.features.shop_list_items_screen.domain.models.ShopListItemModelDomain

fun ShopListItemDto.toDomain(): ShopListItemModelDomain =
    ShopListItemModelDomain(
        created = this.created,
        name = this.name,
        id = this.id,
        isCrossed = this.isCrossed
    )