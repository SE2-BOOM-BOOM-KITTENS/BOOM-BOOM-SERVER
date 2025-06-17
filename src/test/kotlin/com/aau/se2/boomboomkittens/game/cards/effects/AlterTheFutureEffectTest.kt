package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.AlterTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class AlterTheFutureEffectTest {

    @Test
    fun `alter the future rearranges top 3 cards`() {
        val player = Player(playerId = UUID.randomUUID(), name = "Player1", defuseCount = 0, isAlive = true)
        val effect = AlterTheFutureEffect()
        val card = Card(CardType.ALTER_THE_FUTURE)
        val gameLogic = GameLogic(UUID.randomUUID())

        var rearrangedCards: List<Card>? = null
        val cardLogic = object : CardLogic(2, gameLogic) {
        val gameLogic = object : GameLogic(UUID.randomUUID(), mutableListOf()) {
            override fun peekTopCards(count: Int): List<Card> {
                return listOf(
                    Card(CardType.DEFUSE),
                    Card(CardType.BLANK),
                    Card(CardType.ALTER_THE_FUTURE)
                )
            }

            override fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
                rearrangedCards = newOrder
            }
        }

        effect.apply(card, player, cardLogic)

        assertNotNull(rearrangedCards)
        assertEquals("ALTER_THE_FUTURE", rearrangedCards!![0].name)
        assertEquals("BLANK", rearrangedCards!![1].name)
        assertEquals("DEFUSE", rearrangedCards!![2].name)
    }
}
