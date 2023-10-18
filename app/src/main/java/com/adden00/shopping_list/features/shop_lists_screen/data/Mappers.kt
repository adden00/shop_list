package com.adden00.shopping_list.features.shop_lists_screen.data

import com.adden00.shopping_list.features.shop_lists_screen.data.models.ShopListDto
import com.adden00.shopping_list.features.shop_lists_screen.domain.models.ShopListModelDomain


fun ShopListDto.toDomain(): ShopListModelDomain =
    ShopListModelDomain(
        id = this.id,
        name = this.name,
        created = this.created
    )