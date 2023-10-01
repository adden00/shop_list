package com.adden00.testtaskunisafe.features.shop_list_screen.presentation

import com.adden00.testtaskunisafe.features.shop_list_screen.domain.models.ShopListModelDomain
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.models.ShopListModel

fun ShopListModelDomain.toPresentation(): ShopListModel  =
    ShopListModel(
        id = this.id,
        name = this.name,
        created = this.created
    )