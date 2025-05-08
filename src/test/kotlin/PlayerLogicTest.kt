/*package com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import java.util.UUID

class PlayerLogicTest {

    private lateinit var playerCircle: PlayerCircle
    private lateinit var player1: Player
    private lateinit var player2: Player

    @BeforeEach
    fun setUp() {
        playerCircle = PlayerCircle()
        player1 = Player(UUID.randomUUID(),"player1")
        player2 = Player(UUID.randomUUID(),"player2")
    }

    @Test
    fun addPlayerTest(){
        playerCircle.addPlayer(player1)
        assertEquals(player1, playerCircle.getPlayerById(player1.playerId))
        assertEquals(1, playerCircle.getPlayerCount())
    }

    @Test
    fun addPlayerExceptionTest(){
        playerCircle.addPlayer(player1)
        assertThrows(IllegalArgumentException::class.java) {
            playerCircle.addPlayer(player1)
        }
    }

    @Test
    fun removePlayerByIdTest(){
        playerCircle.addPlayer(player1)
        playerCircle.removePlayerById(player1.playerId)
        assertNull(playerCircle.getPlayerById(player1.playerId))
        assertEquals(0, playerCircle.getPlayerCount())
    }

    @Test
    fun nextTurnTest(){
        playerCircle.addPlayer(player1)
        playerCircle.addPlayer(player2)
        val first = playerCircle.getCurrentPlayer()
        playerCircle.nextTurn()
        val second = playerCircle.getCurrentPlayer()
        assertNotEquals(first, second)
    }

    @Test
    fun getPlayerHandTest(){
        val card = Card(CardType.BLANK)
        playerCircle.addPlayer(player1)
        playerCircle.addCardToPlayer(player1.playerId, card)
        assertEquals(1, playerCircle.getPlayerHand(player1.playerId).getCardAmount())

        playerCircle.removeCardFromPlayer(player1.playerId, card)
        assertEquals(0, playerCircle.getPlayerHand(player1.playerId).getCardAmount())
    }

    @Test
    fun getPlayerListTest(){
        playerCircle.addPlayer(player1)
        playerCircle.addPlayer(player2)
        val list = playerCircle.getPlayerList()
        assertTrue(list.containsAll(listOf(player1, player2)))
        assertEquals(2, list.size)
    }

    @Test
    fun getCurrentPlayerTest(){
        playerCircle.addPlayer(player1)
        assertTrue(playerCircle.isCurrentPlayer(player1.playerId))
        playerCircle.addPlayer(player2)
        playerCircle.nextTurn()
        assertTrue(playerCircle.isCurrentPlayer(player2.playerId))
    }
}*/