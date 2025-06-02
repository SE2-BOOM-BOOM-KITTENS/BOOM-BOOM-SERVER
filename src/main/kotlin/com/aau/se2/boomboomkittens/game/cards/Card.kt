package com.aau.se2.boomboomkittens.game.cards

import java.util.UUID

data class Card(
    val type: CardType = CardType.BLANK,
    val id: UUID = UUID.randomUUID(),
    val name: String = type.name,
    val cheatDuplicated: Boolean = false,
    var aliasType: CardType? = null
)

