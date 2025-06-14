package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages

data class ServerMessage(
    val type: String,
    val message: String,
    val payload: Any?
)