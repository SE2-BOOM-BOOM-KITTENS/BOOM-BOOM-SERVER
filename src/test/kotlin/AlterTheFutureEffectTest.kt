package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.AlterTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.cards.Card
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.LinkedList
import java.util.UUID

class AlterTheFutureEffectTest(lobbyId: UUID) {

    private lateinit var gameLogic: GameLogic
    private lateinit var player: Player

    @BeforeEach
    fun setup() {
        gameLogic = GameLogic(
            lobbyId = TODO())
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
    fun `test AlterTheFutureEffect rearranges top 3 cards`() {
        val alterTheFutureEffect = AlterTheFutureEffect()

        // Erstelle neue Anordnung
        val newOrder = listOf(
            Card(type = TODO()),
            Card(type = TODO()),
            Card(type = TODO())
        )

        // Überschreibe GameLogic-Funktion, um Test zu kontrollieren
        val customGameLogic = object : GameLogic(lobbyId = TODO()) {
            override fun peekTopCards(count: Int): List<Card> {
                return listOf(Card(type = TODO()), Card(type = TODO()), Card(type = TODO()))
            }

            override fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
                // Hier wird getestet ob neue Reihenfolge korrekt
                assertEquals("Card3", newOrder[0].name)
                assertEquals("Card1", newOrder[1].name)
                assertEquals("Card2", newOrder[2].name)
            }
        }

        alterTheFutureEffect.apply(player, customGameLogic)
    }
}