package com.aau.se2.boomboomkittens.filipp.server.networkPackage

import java.util.UUID

data class PlayerNetworkPacket(
    val id: UUID,
    val name: String,
    val cardAmount: Int
)