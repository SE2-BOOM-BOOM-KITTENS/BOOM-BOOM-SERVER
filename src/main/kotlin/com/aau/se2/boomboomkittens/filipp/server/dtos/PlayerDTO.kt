package com.aau.se2.boomboomkittens.filipp.server.dtos

import java.util.UUID

data class PlayerDTO(
    val id: UUID,
    val name: String,
    val cardAmount: Int
)