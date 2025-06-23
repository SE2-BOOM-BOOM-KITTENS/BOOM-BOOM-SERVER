package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.ReverseEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ReverseEffectTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var player3: Player

    @BeforeEach
    fun setUp() {
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        player3 = Player(UUID.randomUUID(), "Charlie")

        val players = mutableListOf(player1, player2, player3)
        gameLogic = GameLogic(UUID.randomUUID(), players)
        cardLogic = gameLogic.cardLogic
    }

    @Test
    fun `should reverse player order when ReverseEffect is applied`() {
        val before = gameLogic.playerLogic.getPlayerList().map { it.name }

        val reverseEffect = ReverseEffect()
        val reverseCard = Card(CardType.REVERSE)
        reverseEffect.apply(reverseCard, player1, cardLogic)

        val after = gameLogic.playerLogic.getPlayerList().map { it.name }

        assertEquals(before.reversed(), after)
    }

    @Test
    fun `should move to next player after reverse without drawing`() {
        val currentBefore = gameLogic.playerLogic.getCurrentPlayerNode()
        val nextExpected = currentBefore?.previous?.player

        val reverseEffect = ReverseEffect()
        val reverseCard = Card(CardType.REVERSE)
        reverseEffect.apply(reverseCard, currentBefore!!.player, cardLogic)

        val currentAfter = gameLogic.playerLogic.getCurrentPlayer()

        assertEquals(nextExpected, currentAfter)
    }

    @Test
    fun `should handle reverse with two players`() {
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        val players = mutableListOf(player1, player2)
        gameLogic = GameLogic(UUID.randomUUID(), players)
        cardLogic = gameLogic.cardLogic

        val before = gameLogic.playerLogic.getPlayerList().map { it.name }

        val reverseEffect = ReverseEffect()
        val reverseCard = Card(CardType.REVERSE)
        reverseEffect.apply(reverseCard, player1, cardLogic)

        val after = gameLogic.playerLogic.getPlayerList().map { it.name }

        assertEquals(before.reversed(), after)
    }

    @Test
    fun `should handle reverse with four players`() {
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        player3 = Player(UUID.randomUUID(), "Charlie")
        val player4 = Player(UUID.randomUUID(), "Diana")
        val players = mutableListOf(player1, player2, player3, player4)
        gameLogic = GameLogic(UUID.randomUUID(), players)
        cardLogic = gameLogic.cardLogic

        val before = gameLogic.playerLogic.getPlayerList().map { it.name }

        val reverseEffect = ReverseEffect()
        val reverseCard = Card(CardType.REVERSE)
        reverseEffect.apply(reverseCard, player1, cardLogic)

        val after = gameLogic.playerLogic.getPlayerList().map { it.name }

        assertEquals(before.reversed(), after)
    }
}
