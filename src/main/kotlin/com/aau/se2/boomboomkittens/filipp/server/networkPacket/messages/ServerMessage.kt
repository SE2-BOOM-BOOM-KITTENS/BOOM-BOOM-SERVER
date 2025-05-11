package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages

import com.aau.se2.boomboomkittens.filipp.server.networkPacket.GameStateNetworkPacket

data class ServerMessage(
    val type: String,
    val message: String,
    val gameState: GameStateNetworkPacket?
)