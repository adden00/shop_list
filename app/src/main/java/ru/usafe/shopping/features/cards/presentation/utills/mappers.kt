package ru.usafe.shopping.features.cards.presentation.utills

import ru.usafe.shopping.features.cards.domain.models.CardModel
import ru.usafe.shopping.features.cards.presentation.models.CardModelPres

fun CardModel.toPresentation(): CardModelPres =
    CardModelPres(id, cardName, cardCode, cardQr, cardBarcode, cardHex)