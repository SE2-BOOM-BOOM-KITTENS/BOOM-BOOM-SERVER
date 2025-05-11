package com.aau.se2.boomboomkittens.filipp.server.networkPackage

import com.aau.se2.boomboomkittens.game.cards.CardType

data class CardNetworkPacket(
    val name:String,
    val type: CardType) {
}