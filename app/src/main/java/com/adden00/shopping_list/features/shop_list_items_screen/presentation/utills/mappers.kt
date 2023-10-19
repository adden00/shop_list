package com.adden00.shopping_list.features.shop_list_items_screen.presentation.utills

import com.adden00.shopping_list.features.shop_list_items_screen.domain.models.ShopListItemModelDomain
import com.adden00.shopping_list.features.shop_list_items_screen.presentation.models.ShopListItemModel

fun ShopListItemModelDomain.toPresentation(): ShopListItemModel =
    ShopListItemModel(created, id, name, isCrossed)