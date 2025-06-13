package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.game.cards.CardType

data class CardNetworkPacket(
    val name:String,
    val type: CardType,
    val aliasType: CardType? = null)