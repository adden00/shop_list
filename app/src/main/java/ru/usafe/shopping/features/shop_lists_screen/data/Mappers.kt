package ru.usafe.shopping.features.shop_lists_screen.data

import ru.usafe.shopping.features.shop_lists_screen.data.models.ShopListDto
import ru.usafe.shopping.features.shop_lists_screen.domain.models.ShopListModelDomain


fun ShopListDto.toDomain(): ShopListModelDomain =
    ShopListModelDomain(
        id = this.id,
        name = this.name,
        created = this.created
    )