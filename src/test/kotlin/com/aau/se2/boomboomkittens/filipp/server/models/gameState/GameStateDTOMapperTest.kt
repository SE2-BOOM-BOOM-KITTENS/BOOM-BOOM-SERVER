package com.aau.se2.boomboomkittens.filipp.server.models.gameState

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import java.util.UUID

class GameStateDTOMapperTest {

    private lateinit var gameState: GameState
    private lateinit var mapper: GameStateDTOMapper
    private lateinit var player1: Player
    private lateinit var player2: Player

    @BeforeEach
    fun setUp() {
        mapper = GameStateDTOMapper()

        player1 = Player(UUID.randomUUID(), "player1")
        player2 = Player(UUID.randomUUID(), "player2")

        gameState = GameState(UUID.randomUUID(), mutableListOf(player1,player2))

        gameState.drawPile.insertAt(0, Card("Card1"))
        gameState.discardPile.insertAt(0, Card("Card2"))

        gameState.addCardToPlayer(player1.playerId, Card("Card3"))
        gameState.addCardToPlayer(player2.playerId, Card("Card4"))
    }

    @Test
    fun gameStateToDTOTest(){
        val dto = mapper.gameStateToDTO(gameState)

        assertEquals(gameState.lobbyId, dto.lobbyId)
        assertEquals(2, dto.playerCount)

        assertNotNull(dto.currentPlayer)
        assertNotNull(dto.nextPlayer)
        assertNotNull(dto.drawPile)
        assertNotNull(dto.discardPile)

        assertEquals(2, dto.players.size)
        assertEquals("player1", dto.players[0].name)
        assertEquals("player2", dto.players[1].name)
    }

    @Test
    fun gameStateToDTOWinnerNullTest(){
        val dto = mapper.gameStateToDTO(gameState)
        assertNull(dto.winner)
    }

    @Test
    fun gameStateToDTOWinnerTest(){
        gameState.removePlayer(player2.playerId)
        val dto = mapper.gameStateToDTO(gameState)
        assertNotNull(dto.winner)
        assertEquals(player1.playerId, dto.winner!!.id)
    }
}