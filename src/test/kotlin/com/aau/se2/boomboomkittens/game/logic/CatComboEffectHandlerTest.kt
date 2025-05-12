package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals


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
    fun `test two same Cat cards triggers random steal`() {
        val player1 = Player(UUID.randomUUID(), "Alice")
        val player2 = Player(UUID.randomUUID(), "Bob")
        val card1 = Card(CardType.CAT_TACO)
        val card2 = Card(CardType.CAT_TACO)
        val stolenCard = Card(CardType.DEFUSE)

        player1.playerHand.cards.addAll(listOf(card1, card2))
        player2.playerHand.cards.add(stolenCard)

        CatComboEffectHandler.resolveRandomSteal(player1, player2)

        assertTrue(stolenCard in player1.playerHand.cards)
        assertTrue(stolenCard !in player2.playerHand.cards)
    }

    @Test
    fun `test three same Cat cards triggers specific steal`() {
        player1.playerHand.cards.addAll(listOf(
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_BEARD)
        ))
        val targetCard = Card(CardType.DEFUSE)
        player2.playerHand.cards.add(targetCard)

        CatComboEffectHandler.resolveSpecificSteal(player1, player2, CardType.DEFUSE)

        assertTrue(targetCard in player1.playerHand.cards)
        assertTrue(targetCard !in player2.playerHand.cards)
    }

    @Test
    fun `invalid combo with four same cards does nothing`() {
        repeat(4) {
            player1.playerHand.cards.add(Card(CardType.CAT_BEARD))
        }
        val originalDiscardSize = game.discardPile.getPileList().size

        CatComboEffectHandler.handleCombo(player1, player1.playerHand.cards.toList(), game)

        // Erwartung: keine Aktion, Karten aber abgelegt
        assertEquals(originalDiscardSize + 4, game.discardPile.getPileList().size)
        assertTrue(player1.playerHand.cards.isEmpty())
    }


    @Test
    fun `test five different Cat cards triggers discard pile choice`() {
        val discardCard = Card(CardType.DEFUSE)
        game.discardPile.getPileList().add(discardCard)

        player1.playerHand.cards.addAll(listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_HAIRY_POTATO),
            Card(CardType.CAT_RAINBOW_RALPHING),
            Card(CardType.CAT_CATERMELON)
        ))

        CatComboEffectHandler.resolveDiscardSelection(player1, discardCard, game)

        assertTrue(discardCard in player1.playerHand.cards)
        assertTrue(discardCard !in game.discardPile.getPileList())
    }

}