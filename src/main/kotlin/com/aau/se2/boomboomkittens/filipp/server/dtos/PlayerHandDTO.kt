package com.aau.se2.boomboomkittens.filipp.server.dtos

import java.util.UUID

data class PlayerHandDTO(
    val playerId: UUID,
    val cards: List<CardDTO>?
)