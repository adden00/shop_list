package ru.usafe.shopping.features.shop_lists.data

import ru.usafe.shopping.features.shop_lists.data.models.ShopListDto
import ru.usafe.shopping.features.shop_lists.domain.models.ShopListModelDomain


fun ShopListDto.toDomain(): ShopListModelDomain =
    ShopListModelDomain(
        id = this.id,
        name = this.name,
        created = this.created
    )