package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
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
    private lateinit var game: GameLogic

    @BeforeEach
    fun setup() {
        playerId = UUID.randomUUID()
        player = Player(playerId, "TestPlayer")
        falseId = UUID.randomUUID()
        falsePlayer = Player(falseId, "FalsePlayer")
        game = GameLogic(UUID.randomUUID(), mutableListOf())
        cardLogic = CardLogic(1,game)
        cardLogic.addPlayer(player)
        player.playerHand.clear()
    }

    @Test
    fun cheatDuplicateCardTest() {
        cardLogic.addCardToPlayer(playerId, dummyCard)

        val duplicate = cardLogic.cheatDuplicateCard(playerId, dummyCard.id)

        assertTrue(player.playerHand.containsCard(dummyCard))
        assertTrue(player.playerHand.containsCard(duplicate))
        assertEquals(2, player.playerHand.getCardAmount())

        assertTrue(duplicate.cheatDuplicated)
    }

//    @Test
//    fun isCardDuplicateTest() {
//        cardLogic.addCardToPlayer(playerId, dummyCard)
//        assertTrue(player.playerHand.containsCard(dummyCard))
//
//        val duplicate = cardLogic.cheatDuplicateCard(playerId, dummyCard.id)
//
//        val result1 = cardLogic.isCardDuplicate(playerId, duplicate.id)
//        assertTrue(result1)
//
//        val result2 = cardLogic.isCardDuplicate(playerId, dummyCard.id)
//        assertFalse(result2)
//    }

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
        val card = Card(CardType.BLANK)

        // Echte Hand aus CardLogic holen
        val hand = cardLogic.getPlayerHand(playerId) ?: error("Player hand not found")

        // Handkarten vollständig leeren, damit Test isoliert ist
        hand.cards.clear()

        // Karte hinzufügen
        hand.addCard(card)

        // fixme split test into add and remove card where the first tests is the precondition for the second
        // Vorher prüfen, dass sie da ist
        assertTrue(hand.containsCardType(card.type), "Card should be present before removal")

        // Karte entfernen
        cardLogic.removeCardFromPlayer(playerId, card)

        // Nachher prüfen, dass sie weg ist
        assertFalse(hand.containsCardType(card.type), "Card should no longer be present after removal")
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