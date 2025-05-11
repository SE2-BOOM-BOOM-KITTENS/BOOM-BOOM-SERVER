package com.aau.se2.boomboomkittens.filipp.server.networkPacket

data class CardPileNetworkPacket(
    val cardCount: Int,
    val cards: List<CardNetworkPacket>?,
)