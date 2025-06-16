package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.SeeTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.PlayerLogic
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class SeeTheFutureEffectTest {

    @Test
    fun `see the future reveals top 3 cards`() {
        val player = Player(playerId = UUID.randomUUID(), name = "Player1", defuseCount = 0, isAlive = true)
        val effect = SeeTheFutureEffect()
        val card = Card(CardType.SEE_THE_FUTURE)

        val expectedTopCards = listOf(
            Card(CardType.SEE_THE_FUTURE),
            Card(CardType.BLANK),
            Card(CardType.DEFUSE)
        )

        val cardLogic = object : CardLogic(2, playerLogic = PlayerLogic()) {
            override fun peekTopCards(count: Int): List<Card> {
                return expectedTopCards
            }
        }

        effect.apply(card, player, cardLogic)

        val actualTopCards = cardLogic.peekTopCards(3)

        assertEquals(expectedTopCards.size, actualTopCards.size)
        assertEquals(expectedTopCards[0].name, actualTopCards[0].name)
        assertEquals(expectedTopCards[1].name, actualTopCards[1].name)
        assertEquals(expectedTopCards[2].name, actualTopCards[2].name)
    }
}