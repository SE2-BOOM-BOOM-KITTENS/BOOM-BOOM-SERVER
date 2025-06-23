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
        // Baue ein Dummy GameLogic mit 3 Spielern
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        player3 = Player(UUID.randomUUID(), "Charlie")

        val players = mutableListOf(player1, player2, player3)
        gameLogic = GameLogic(UUID.randomUUID(), players)
        cardLogic = gameLogic.cardLogic
    }

    @Test
    fun `should reverse player order when ReverseEffect is applied`() {
        // Vorher: normaler Ring
        val before = gameLogic.playerLogic.getPlayerList().map { it.name }

        // Wende ReverseEffect an
        val reverseEffect = ReverseEffect()
        val reverseCard = Card(CardType.REVERSE)
        reverseEffect.apply(reverseCard, player1, cardLogic)

        // Nachher: Reihenfolge umgedreht!
        val after = gameLogic.playerLogic.getPlayerList().map { it.name }

        // Überprüfe, ob die Reihenfolge wirklich gedreht wurde
        assertEquals(before.reversed(), after)
    }

    @Test
    fun `should move to next player after reverse without drawing`() {
        val currentBefore = gameLogic.playerLogic.getCurrentPlayerNode()
        val nextExpected = currentBefore?.previous?.player  // Nach Reverse zum vorherigen Knoten

        val reverseEffect = ReverseEffect()
        val reverseCard = Card(CardType.REVERSE)
        reverseEffect.apply(reverseCard, currentBefore!!.player, cardLogic)

        val currentAfter = gameLogic.playerLogic.getCurrentPlayer()

        assertEquals(nextExpected, currentAfter)
    }
}
