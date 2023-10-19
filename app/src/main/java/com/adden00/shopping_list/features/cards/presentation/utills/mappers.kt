package com.adden00.shopping_list.features.cards.presentation.utills

import com.adden00.shopping_list.features.cards.domain.models.CardModel
import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres

fun CardModel.toPresentation(): CardModelPres =
    CardModelPres(id, cardName, cardCode, cardQr, cardBarcode, cardHex)