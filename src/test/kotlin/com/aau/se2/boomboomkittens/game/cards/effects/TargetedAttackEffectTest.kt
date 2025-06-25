package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.effects.TargetedAttackEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class TargetedAttackEffectTest {

    private lateinit var attacker: Player
    private lateinit var victim: Player
    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic

    @BeforeEach
    fun setup() {
        attacker = Player(name = "Attacker")
        victim = Player(name = "Victim")

        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(attacker, victim))
        cardLogic = gameLogic.cardLogic

        gameLogic.cardLogic.drawPile.clear()
        setCurrentPlayer(attacker)
    }


    private fun setCurrentPlayer(player: Player) {
        val tries = gameLogic.playerLogic.getPlayerCount() + 1
        repeat(tries) {
            if (gameLogic.playerLogic.getCurrentPlayer()?.playerId == player.playerId) return
            gameLogic.playerLogic.moveToNextPlayer()
        }
        throw IllegalStateException("Could not set current player to ${player.name}")
    }

    private fun getVictimFromGame(): Player {
        return gameLogic.playerLogic.getPlayerList().find { it.name == "Victim" }
            ?: throw IllegalStateException("Victim not found")
    }

    @Test
    fun `targeted attack draws 2 cards if available`() {
        gameLogic.cardLogic.drawPile.add(Card(CardType.SHUFFLE))
        gameLogic.cardLogic.drawPile.add(Card(CardType.NOPE))

        val effect = TargetedAttackEffect()
        val card = Card(CardType.TARGETED_ATTACK)

        effect.apply(card, attacker, cardLogic)

        val target = getVictimFromGame()
        assertEquals(10, target.playerHand.getCardAmount())
        assertEquals(0, gameLogic.cardLogic.drawPile.size)
    }

    @Test
    fun `targeted attack draws 1 card if only one exists`() {
        gameLogic.cardLogic.drawPile.add(Card(CardType.ATTACK))

        val effect = TargetedAttackEffect()
        val card = Card(CardType.TARGETED_ATTACK)

        effect.apply(card, attacker, cardLogic)

        val target = getVictimFromGame()
        assertEquals(9, target.playerHand.getCardAmount())
        assertEquals(0, gameLogic.cardLogic.drawPile.size)
    }

    @Test
    fun `targeted attack draws 0 cards if draw pile is empty`() {
        val effect = TargetedAttackEffect()
        val card = Card(CardType.TARGETED_ATTACK)

        effect.apply(card, attacker, cardLogic)

        val target = getVictimFromGame()
        assertEquals(8, target.playerHand.getCardAmount())
        assertEquals(0, gameLogic.cardLogic.drawPile.size)
    }
}