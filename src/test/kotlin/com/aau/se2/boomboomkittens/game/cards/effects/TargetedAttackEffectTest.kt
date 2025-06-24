package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class TargetedAttackEffectTest {

    private lateinit var attacker: Player
    private lateinit var target: Player
    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic

    @BeforeEach
    fun setup() {
        attacker = Player(name = "Attacker")
        target = Player(name = "Target")

        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(attacker, target))
        cardLogic = gameLogic.cardLogic

        gameLogic.drawPile.clear()
    }

    @Test
    fun `target draws 2 cards from draw pile`() {
        gameLogic.drawPile.add(Card(CardType.SHUFFLE))
        gameLogic.drawPile.add(Card(CardType.NOPE))

        val card = Card(CardType.TARGETED_ATTACK)
        val effect = TargetedAttackEffect() // direkte Verwendung ohne Registry

        effect.apply(card, attacker, cardLogic)

        assertEquals(2, target.playerHand.getCardAmount())
        assertEquals(0, gameLogic.drawPile.size)
    }

    @Test
    fun `target draws only 1 card if only one exists`() {
        gameLogic.drawPile.add(Card(CardType.DEFUSE))

        val card = Card(CardType.TARGETED_ATTACK)
        val effect = TargetedAttackEffect()

        effect.apply(card, attacker, cardLogic)

        assertEquals(1, target.playerHand.getCardAmount())
        assertEquals(0, gameLogic.drawPile.size)
    }

    @Test
    fun `target draws nothing if draw pile is empty`() {
        val card = Card(CardType.TARGETED_ATTACK)
        val effect = TargetedAttackEffect()

        effect.apply(card, attacker, cardLogic)

        assertEquals(0, target.playerHand.getCardAmount())
        assertEquals(0, gameLogic.drawPile.size)
    }
}