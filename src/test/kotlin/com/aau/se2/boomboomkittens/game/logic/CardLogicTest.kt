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
    private lateinit var playerId: UUID
    private val dummyCard = Card(CardType.BLANK)

    @BeforeEach
    fun setup() {
        playerId = UUID.randomUUID()
        player = Player(playerId, "TestPlayer")
        cardLogic = CardLogic(1)
        cardLogic.addPlayer(player)
    }

    @Test
    fun `addCardToPlayer adds card to player's hand`() {
        cardLogic.addCardToPlayer(playerId, dummyCard)
        assertTrue(player.playerHand.containsCard(dummyCard))
    }

    @Test
    fun `removeCardFromPlayer removes card from player's hand`() {
        player.playerHand.addCard(dummyCard)
        cardLogic.removeCardFromPlayer(playerId, dummyCard)
        assertFalse(player.playerHand.containsCard(dummyCard))
    }

    @Test
    fun `drawCard adds card from pile to player`() {
        val topCard = Card(CardType.BLANK)
        cardLogic.drawPile.insertAt(0, topCard)
        cardLogic.drawCard(playerId)
        assertTrue(player.playerHand.containsCard(topCard))
    }

    @Test
    fun `drawCard throws if draw pile is empty`() {
        cardLogic.drawPile = CardPile()
        assertThrows(IllegalStateException::class.java) {
            cardLogic.drawCard(playerId)
        }
    }

}