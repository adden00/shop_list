package com.adden00.testtaskunisafe.features.shop_lists_screen.presentation

import com.adden00.testtaskunisafe.features.shop_lists_screen.domain.models.ShopListModelDomain
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.models.ShopListModel

fun ShopListModelDomain.toPresentation(): ShopListModel  =
    ShopListModel(
        id = this.id,
        name = this.name,
        created = this.created
    )