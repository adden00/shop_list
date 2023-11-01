package ru.usafe.shopping.features.shop_lists.presentation

import ru.usafe.shopping.features.shop_lists.domain.models.ShopListModelDomain
import ru.usafe.shopping.features.shop_lists.presentation.models.ShopListModel

fun ShopListModelDomain.toPresentation(): ShopListModel =
    ShopListModel(
        id = this.id,
        name = this.name,
        created = this.created
    )