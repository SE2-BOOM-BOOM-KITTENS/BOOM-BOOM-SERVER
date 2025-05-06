package com.aau.se2.boomboomkittens.filipp.server.models.gameState

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GameStateTest {

    private lateinit var gameState: GameState
    private lateinit var player1: Player
    private lateinit var player2: Player

    @BeforeEach
    fun setUp() {
        player1 = Player(UUID.randomUUID(), "player1")
        player2 = Player(UUID.randomUUID(), "player2")
        gameState = GameState(UUID.randomUUID(),mutableListOf(player1, player2) )
    }

    @Test
    fun initTest(){
        assertEquals(2, gameState.playerCircle.getPlayerCount())
        assertNotNull(gameState.playerCircle.getPlayerById(player1.playerId))
        assertNotNull(gameState.playerCircle.getPlayerById(player2.playerId))
    }

    @Test
    fun drawCardTest(){
        val card = Card("Test Card")
        gameState.drawPile.insertAt(0, card)

        gameState.drawCard(player1.playerId)

        val hand = gameState.playerCircle.getPlayerHand(player1.playerId)
        assertEquals(1, hand.getCardAmount())
        assertEquals(card, hand.cards[0])
    }

    @Test
    fun drawCardExceptionTest(){
        assertTrue(gameState.drawPile.isEmpty())

        val exception = assertThrows<IllegalStateException> {
            gameState.drawCard(player1.playerId)
        }

        assertEquals("Cannot draw from empty pile", exception.message)
    }

    @Test
    fun removePlayerTest(){
        assertEquals(2, gameState.playerCircle.getPlayerCount())

        gameState.removePlayer(player1.playerId)

        assertEquals(1, gameState.playerCircle.getPlayerCount())
        assertNull(gameState.playerCircle.getPlayerById(player1.playerId))
    }

    @Test
    fun addCardToPlayerTest(){
        val card = Card("Boom Card")

        gameState.addCardToPlayer(player2.playerId, card)

        val hand = gameState.playerCircle.getPlayerHand(player2.playerId)
        assertEquals(1, hand.getCardAmount())
        assertEquals(card, hand.cards[0])
    }

    @Test
    fun removeCardFromPlayerTest(){
        val card = Card("Exploding Kitten")
        gameState.addCardToPlayer(player1.playerId, card)

        gameState.removeCardFromPlayer(player1.playerId, card)

        val hand = gameState.playerCircle.getPlayerHand(player1.playerId)
        assertEquals(0, hand.getCardAmount())
    }

    @Test
    fun putCardToDiscardPileTest(){
        val card = Card("Discard Me")
        gameState.putCardToDiscardPile(card)

        val pile = gameState.discardPile.getPileList()
        assertEquals(1, pile.size)
        assertEquals(card, pile[0])
    }

    @Test
    fun getWinnerTest(){
        gameState.playerCircle.removePlayerById(player2.playerId)
        val winner = gameState.getWinner()
        assertEquals(player1.playerId, winner?.playerId)
    }

    @Test
    fun getWinnerTestNull(){
        assertNull(gameState.getWinner())
    }
}