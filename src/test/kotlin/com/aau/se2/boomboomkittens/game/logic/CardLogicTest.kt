package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class CardLogicTest {

    private lateinit var cardLogic: CardLogic
    private lateinit var player: Player
    private lateinit var falsePlayer: Player
    private lateinit var playerId: UUID
    private lateinit var falseId: UUID
    private val dummyCard = Card(CardType.BLANK)

    @BeforeEach
    fun setup() {
        playerId = UUID.randomUUID()
        player = Player(playerId, "TestPlayer")

        falseId = UUID.randomUUID()
        falsePlayer = Player(falseId, "FalsePlayer")
        cardLogic = CardLogic(1)
        cardLogic.addPlayer(player)
    }

    @Test
    fun addCardToPlayerTest() {
        cardLogic.addCardToPlayer(playerId, dummyCard)
        assertTrue(player.playerHand.containsCard(dummyCard))
    }

    @Test
    fun addCardToPlayerTestNull() {
        assertThrows(IllegalArgumentException::class.java) {
            cardLogic.addCardToPlayer(falseId, dummyCard)
        }
    }

    @Test
    fun removeCardFromPlayerTest() {
        player.playerHand.addCard(dummyCard)
        cardLogic.removeCardFromPlayer(playerId, dummyCard)
        assertFalse(player.playerHand.containsCard(dummyCard))
    }

    @Test
    fun removeCardFromPlayerTestNull() {
        assertThrows(IllegalArgumentException::class.java) {
            cardLogic.removeCardFromPlayer(falseId, dummyCard)
        }
    }

    @Test
    fun drawCardTest() {
        val topCard = Card(CardType.BLANK)
        cardLogic.drawPile.insertAt(0, topCard)
        cardLogic.drawCard(playerId)
        assertTrue(player.playerHand.containsCard(topCard))
    }

    @Test
    fun drawCardExceptionTest() {
        cardLogic.drawPile = CardPile()
        assertThrows(IllegalStateException::class.java) {
            cardLogic.drawCard(playerId)
        }
    }

}