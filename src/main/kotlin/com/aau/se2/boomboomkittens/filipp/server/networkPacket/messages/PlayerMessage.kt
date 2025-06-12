package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages

data class PlayerMessage(
    val playerName: String?,
    val action: String?,
    val payload: Any?,
    val targetId: String? = null
    )