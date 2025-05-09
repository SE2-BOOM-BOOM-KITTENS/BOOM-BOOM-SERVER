package com.aau.se2.boomboomkittens.game.logic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.SeeTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.cards.Card
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.LinkedList

class SeeTheFutureEffectTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var player: Player

    @BeforeEach
    fun setup() {
        gameLogic = GameLogic(lobbyId = TODO())
        player = Player(
            name = "TestPlayer", id = TODO())

        // Erstelle ein paar Testkarten
        val testCards = listOf(
            Card(type = TODO()),
            Card(type = TODO()),
            Card(type = TODO()),
            Card(type = TODO()),
            Card(type = TODO())
        )

        // drawPile setzen
        val drawPileField = GameLogic::class.java.getDeclaredField("drawPile")
        drawPileField.isAccessible = true
        val drawPile = LinkedList<Card>(testCards)
        drawPileField.set(gameLogic, drawPile)
    }

    @Test
    fun `test SeeTheFutureEffect shows top 3 cards`() {
        val seeTheFutureEffect = SeeTheFutureEffect()
        seeTheFutureEffect.apply(player, gameLogic)

        val topCards = gameLogic.peekTopCards(3)

        assertEquals(3, topCards.size)
        assertEquals("Card1", topCards[0].name)
        assertEquals("Card2", topCards[1].name)
        assertEquals("Card3", topCards[2].name)
    }
}