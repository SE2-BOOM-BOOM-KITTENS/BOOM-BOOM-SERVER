package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages

import java.util.UUID

data class CatComboMessage(
    val type: ComboType,
    val fromPlayerId: UUID,
    val toPlayerId: UUID? = null,
    val cardName: String? = null
)
