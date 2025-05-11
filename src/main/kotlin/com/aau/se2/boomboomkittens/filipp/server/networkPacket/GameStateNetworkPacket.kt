package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import java.util.UUID

data class GameStateNetworkPacket(
    val lobbyId: UUID,
    val playerCount: Int,
    val players: MutableList<PlayerNetworkPacket>,
    val currentPlayer: PlayerNetworkPacket,
    val nextPlayer: PlayerNetworkPacket,
    val winner: PlayerNetworkPacket?,
    val drawPile: CardPileNetworkPacket,
    val discardPile: CardPileNetworkPacket
    )