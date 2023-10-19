package com.adden00.shopping_list.features.cards.data

import com.adden00.shopping_list.features.cards.data.models.CardDto
import com.adden00.shopping_list.features.cards.domain.models.CardModel

fun CardDto.toDomain(): CardModel =
    CardModel(id, cardName, cardCode, cardQr, cardBarcode, cardHex)