package ru.usafe.shopping.features.cards.data

import ru.usafe.shopping.features.cards.data.models.CardDto
import ru.usafe.shopping.features.cards.domain.models.CardModel

fun CardDto.toDomain(): CardModel =
    CardModel(id, cardName, cardCode, cardQr, cardBarcode, cardHex)