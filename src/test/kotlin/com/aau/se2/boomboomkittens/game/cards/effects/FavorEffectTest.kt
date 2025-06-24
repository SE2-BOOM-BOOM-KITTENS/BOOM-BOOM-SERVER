package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.FavorEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FavorEffectTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var player: Player
    private lateinit var target: Player

    @BeforeEach
    fun setup() {
        player = Player(name = "Player")
        target = Player(name = "Target")

        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player, target))
        cardLogic = gameLogic.cardLogic
        gameLogic.drawPile.clear()

        // Hand vorbereiten
        target.playerHand.addCard(Card(CardType.NOPE))
    }

    @Test
    fun `favor steals card from next player`() {
        val effect = FavorEffect()
        val favorCard = Card(CardType.FAVOR)

        assertEquals(0, player.playerHand.getCardAmount())
        assertEquals(1, target.playerHand.getCardAmount())

        effect.apply(favorCard, player, cardLogic)

        assertEquals(1, player.playerHand.getCardAmount())
        assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `favor does nothing if next player has no cards`() {
        target.playerHand.getRandomCard()?.let {
            target.playerHand.removeCard(it)
        }

        val effect = FavorEffect()
        val favorCard = Card(CardType.FAVOR)

        effect.apply(favorCard, player, cardLogic)

        assertEquals(0, player.playerHand.getCardAmount())
        assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `favor throws when no target player exists`() {
        val soloGame = GameLogic(UUID.randomUUID(), mutableListOf(player))
        val logic = soloGame.cardLogic
        val effect = FavorEffect()
        val favorCard = Card(CardType.FAVOR)

        assertFailsWith<IllegalStateException> {
            effect.apply(favorCard, player, logic)
        }
    }
}