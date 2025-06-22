package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards

import com.aau.se2.boomboomkittens.game.cards.CardType

data class CardDefinition(
    val type: CardType,
    val withPawCount: Int,
    val withoutPawCount: Int
)