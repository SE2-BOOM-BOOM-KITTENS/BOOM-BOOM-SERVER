package com.aau.se2.boomboomkittens.filipp.server.models.gameState

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class GameLogicTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var player1: Player
    private lateinit var player2: Player

    @BeforeEach
    fun setUp() {
        player1 = Player(UUID.randomUUID().toString(), "player1")
        player2 = Player(UUID.randomUUID().toString(), "player2")
        gameLogic = GameLogic(UUID.randomUUID(),mutableListOf(player1, player2) )
    }

    @Test
    fun initTest(){
        assertEquals(2, gameLogic.playerLogic.getPlayerCount())
        assertNotNull(gameLogic.playerLogic.getPlayerById(player1.playerId))
        assertNotNull(gameLogic.playerLogic.getPlayerById(player2.playerId))
    }

    @Test
    fun drawCardTest(){
        val card = Card(CardType.BLANK)
        cardLogic.drawPile.insertAt(0, card)

        cardLogic.drawCard(player1.playerId)

        val hand = gameLogic.getPlayerHand(player1.playerId)
        assertEquals(1, hand.getCardAmount())
        assertEquals(card, hand.cards[0])
    }

    @Test
    fun drawCardExceptionTest(){
        assertTrue(cardLogic.drawPile.isEmpty())

        val exception = assertThrows<IllegalStateException> {
            cardLogic.drawCard(player1.playerId)
        }

        assertEquals("Cannot draw from empty pile", exception.message)
    }

    @Test
    fun removePlayerTest(){
        assertEquals(2, gameLogic.playerLogic.getPlayerCount())

        gameLogic.removePlayer(player1.playerId)

        assertEquals(1, gameLogic.playerLogic.getPlayerCount())
        assertNull(gameLogic.playerLogic.getPlayerById(player1.playerId))
    }

    @Test
    fun addCardToPlayerTest(){
        val card = Card(CardType.EXPLODING_KITTEN)

        cardLogic.addCardToPlayer(player2.playerId, card)

        val hand = gameLogic.getPlayerHand(player2.playerId)
        assertEquals(1, hand.getCardAmount())
        assertEquals(card, hand.cards[0])
    }

    @Test
    fun removeCardFromPlayerTest(){
        val card = Card(CardType.EXPLODING_KITTEN)
        cardLogic.addCardToPlayer(player1.playerId, card)

        cardLogic.removeCardFromPlayer(player1.playerId, card)

        val hand = gameLogic.getPlayerHand(player1.playerId)
        assertEquals(0, hand.getCardAmount())
    }

    /*@Test
    fun putCardToDiscardPileTest(){
        val card = Card(CardType.DEFUSE)
        gameLogic.putCardToDiscardPile(card)

        val pile = gameLogic.discardPile.getPileList()
        assertEquals(1, pile.size)
        assertEquals(card, pile[0])
    }*/

    @Test
    fun getWinnerTest(){
        gameLogic.playerLogic.removePlayerById(player2.playerId)
        val winner = gameLogic.getWinner()
        assertEquals(player1.playerId, winner?.playerId)
    }

    @Test
    fun getWinnerTestNull(){
        assertNull(gameLogic.getWinner())
    }
}