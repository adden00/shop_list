package ru.usafe.shopping.features.shop_lists_screen.presentation

import ru.usafe.shopping.features.shop_lists_screen.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists_screen.presentation.models.ShopListModel

fun ShopListModelDomain.toPresentation(): ShopListModel =
    ShopListModel(
        id = this.id,
        name = this.name,
        created = this.created
    )