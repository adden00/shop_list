package ru.usafe.shopping.features.shop_list_items_screen.presentation.utills

import ru.usafe.shopping.features.shop_list_items_screen.domain.models.ShopListItemModelDomain
import ru.usafe.shopping.features.shop_list_items_screen.presentation.models.ShopListItemModel

fun ShopListItemModelDomain.toPresentation(): ShopListItemModel =
    ShopListItemModel(created, id, name, isCrossed)