package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class TargetedAttackEffectTest {

    private fun testCard(name: String = "TestCard"): Card =
        Card(type = CardType.BLANK, name = name)

    private fun createCardLogicWithGame(attacker: Player, target: Player, vararg drawCards: Card): Triple<GameLogic, CardLogic, TargetedAttackEffect> {
        val players = mutableListOf(attacker, target)
        val gameLogic = GameLogic(UUID.randomUUID(), players)
        val cardLogic = gameLogic.cardLogic

        gameLogic.drawPile.addAll(drawCards)

        val effect = TargetedAttackEffect(target)

        return Triple(gameLogic, cardLogic, effect)
    }

    @Test
    fun `apply draws 2 cards for target`() {
        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")
        val card = testCard()

        val (gameLogic, cardLogic, effect) = createCardLogicWithGame(attacker, target,
            testCard("A"), testCard("B"), testCard("C"))

        effect.apply(card, attacker, cardLogic)

        assertEquals(2, target.playerHand.getCardAmount())
        assertEquals(1, gameLogic.drawPile.size)
    }

    @Test
    fun `apply handles only 1 card in draw pile`() {
        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")
        val card = testCard()

        val (gameLogic, cardLogic, effect) = createCardLogicWithGame(attacker, target,
            testCard("Only"))

        effect.apply(card, attacker, cardLogic)

        assertEquals(1, target.playerHand.getCardAmount())
        assertTrue(gameLogic.drawPile.isEmpty())
    }

    @Test
    fun `apply handles empty draw pile`() {
        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")
        val card = testCard()

        val (gameLogic, cardLogic, effect) = createCardLogicWithGame(attacker, target)

        effect.apply(card, attacker, cardLogic)

        assertEquals(0, target.playerHand.getCardAmount())
        assertTrue(gameLogic.drawPile.isEmpty())
    }
}