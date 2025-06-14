package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.game.cards.CardType
import java.util.UUID

data class CardNetworkPacket(
    val name:String = "",
    val type: CardType = CardType.BLANK,
    val id: UUID = UUID.randomUUID(),
    val cheatDuplicated: Boolean = false,
    val aliasType: CardType? = null) {
}