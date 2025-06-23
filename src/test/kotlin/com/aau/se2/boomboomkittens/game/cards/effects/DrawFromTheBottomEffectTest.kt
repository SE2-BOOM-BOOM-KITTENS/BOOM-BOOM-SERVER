package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.DrawFromTheBottomEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class DrawFromTheBottomEffectTest {

    private lateinit var player: Player
    private lateinit var cardLogic: CardLogic
    private lateinit var gameLogic: GameLogic

    @BeforeEach
    fun setUp() {
        player = Player(playerId = UUID.randomUUID(), name = "TestPlayer")
        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player))
        cardLogic = gameLogic.cardLogic

        cardLogic.drawPile.clear()
        repeat(5) { i ->
            cardLogic.drawPile.add(Card(CardType.CAT_TACO, name = "Card$i"))
        }
    }

    @Test
    fun `should draw bottom card and add to hand`() {
        val drawFromBottomEffect = DrawFromTheBottomEffect()
        val secondPlayer = Player(playerId = UUID.randomUUID(), name = "SecondPlayer")
        gameLogic.playerLogic.addPlayerByID(secondPlayer)

        val initialCurrentPlayer = gameLogic.playerLogic.getCurrentPlayer()
        val initialDeckSize = cardLogic.drawPile.size
        val initialBottomCard = cardLogic.drawPile.getPileList().last()

        drawFromBottomEffect.apply(
            Card(CardType.DRAW_FROM_THE_BOTTOM),
            player,
            cardLogic
        )

        assertTrue(player.playerHand.cards.contains(initialBottomCard))
        assertEquals(initialDeckSize - 1, cardLogic.drawPile.size)
        assertFalse(cardLogic.drawPile.getPileList().contains(initialBottomCard))

        val newCurrentPlayer = gameLogic.playerLogic.getCurrentPlayer()
        assertNotEquals(initialCurrentPlayer, newCurrentPlayer)
        assertEquals(secondPlayer, newCurrentPlayer)
    }
}
