package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages

import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket

data class PlayerMessage(
    val playerName: String?,
    val action: String?,
    val cardsPlayed: List<CardNetworkPacket>?,
    val targetId: String? = null
    )