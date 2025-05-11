package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import java.util.UUID

data class PlayerHandNetworkPacket(
    val playerId: UUID,
    val cards: List<CardNetworkPacket>?
)