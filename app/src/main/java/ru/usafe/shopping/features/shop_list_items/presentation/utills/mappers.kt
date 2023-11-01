package ru.usafe.shopping.features.shop_list_items.presentation.utills

import ru.usafe.shopping.features.shop_list_items.domain.models.ShopListItemModelDomain
import ru.usafe.shopping.features.shop_list_items.presentation.models.ShopListItemModel

fun ShopListItemModelDomain.toPresentation(): ShopListItemModel =
    ShopListItemModel(created, id, name, isCrossed)