package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.AlterTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.LinkedList
import java.util.UUID

class AlterTheFutureEffectTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var player: Player
    private val lobbyId = UUID.randomUUID()

    @BeforeEach
    fun setup() {
        gameLogic = GameLogic(
            lobbyId = lobbyId
        )
        player = Player(
            name = "TestPlayer",
            id = UUID.randomUUID().toString()
        )

        val testCards = listOf(
            Card(type = CardType.ALTERTHEFUTURE),
            Card(type = CardType.SEETHEFUTURE),
            Card(type = CardType.BLANK),
            Card(type = CardType.DEFUSE),
            Card(type = CardType.EXPLODING_KITTEN)
        )

        val drawPileField = GameLogic::class.java.getDeclaredField("drawPile")
        drawPileField.isAccessible = true
        val drawPile = LinkedList<Card>(testCards)
        drawPileField.set(gameLogic, drawPile)
    }

    @Test
    fun `test AlterTheFutureEffect rearranges top 3 cards`() {
        val alterTheFutureEffect = AlterTheFutureEffect()

        val customGameLogic = object : GameLogic(lobbyId = lobbyId) {
            override fun peekTopCards(count: Int): List<Card> {
                return listOf(
                    Card(type = CardType.EXPLODING_KITTEN),   // Card1
                    Card(type = CardType.DEFUSE),    // Card2
                    Card(type = CardType.EXPLODING_KITTEN)     // Card3
                )
            }

            override fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
                assertEquals("Attack", newOrder[0].name)
                assertEquals("Shuffle", newOrder[1].name)
                assertEquals("Defuse", newOrder[2].name)
            }
        }

        alterTheFutureEffect.apply(player, customGameLogic)
    }
}