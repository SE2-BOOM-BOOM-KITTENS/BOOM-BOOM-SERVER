package com.aau.se2.boomboomkittens.filipp.server.models.dtos

import java.util.UUID

data class GameStateDTO(
    val lobbyId: UUID,
    val playerCount: Int,
    val players: MutableList<PlayerDTO>,
    val currentPlayer: PlayerDTO,
    val nextPlayer: PlayerDTO,
    val winner: PlayerDTO?,
    val drawPile: CardPileDTO,
    val discardPile: CardPileDTO
    ) {
}