package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.filipp.server.dtos.messages.ComboType
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID


class CatComboEffectHandlerTest {

    private lateinit var game: GameLogic
    private lateinit var player1: Player
    private lateinit var player2: Player

    @BeforeEach
    fun setup() {
        player1 = Player(playerId = UUID.randomUUID(), name = "Alice")
        player2 = Player(playerId = UUID.randomUUID(), name = "Bob")
        game = GameLogic(lobbyId = UUID.randomUUID(), players = mutableListOf(player1, player2))
        game.discardPile.getPileList().clear()
        player1.playerHand.cards.clear()
        player2.playerHand.cards.clear()
    }

    @Test
    fun `handleCombo returns RANDOM_STEAL and performs action`() {
        val card1 = Card(CardType.CAT_TACO)
        val card2 = Card(CardType.CAT_TACO)
        val stolenCard = Card(CardType.DEFUSE)

        player1.playerHand.cards.addAll(listOf(card1, card2))
        player2.playerHand.cards.add(stolenCard)

        val result = CatComboEffectHandler.handleCombo(player1, listOf(card1, card2), game)

        assertEquals(ComboType.RANDOM_STEAL, result?.type)
        assertTrue(stolenCard in player1.playerHand.cards)
        assertTrue(stolenCard !in player2.playerHand.cards)
    }

    @Test
    fun `invalid combo returns null and discards cards`() {
        val cards = List(4) { Card(CardType.CAT_BEARD) }
        player1.playerHand.cards.addAll(cards)

        val result = CatComboEffectHandler.handleCombo(player1, cards, game)

        assertEquals(null, result)
        assertTrue(player1.playerHand.cards.isEmpty())
        assertTrue(game.discardPile.getPileList().containsAll(cards))
    }

    @Test
    fun `test five different Cat cards triggers discard pile choice`() {
        val discardCard = Card(CardType.DEFUSE)
        game.discardPile.getPileList().add(discardCard)

        val comboCards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_HAIRY_POTATO),
            Card(CardType.CAT_RAINBOW_RALPHING),
            Card(CardType.CAT_CATERMELON)
        )
        player1.playerHand.cards.addAll(comboCards)

        val result = CatComboEffectHandler.handleCombo(player1, comboCards, game)

        assertEquals(ComboType.DISCARD_RETRIEVE, result?.type)
        assertTrue(discardCard in player1.playerHand.cards)
        assertFalse(discardCard in game.discardPile.getPileList())
        assertTrue(player1.playerHand.cards.none { it in comboCards })
    }

    @Test
    fun `three same Cat cards triggers SPECIFIC_REQUEST`() {
        val comboCards = List(3) { Card(CardType.CAT_BEARD) }
        player1.playerHand.cards.addAll(comboCards)

        val result = CatComboEffectHandler.handleCombo(player1, comboCards, game)

        assertEquals(ComboType.SPECIFIC_REQUEST, result?.type)
    }


}