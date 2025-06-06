package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class GameInitializationTest {

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4])
    fun `deck and card distribution works correctly for player counts`(playerCount: Int) {
        // Spieler erstellen
        val players = (1..playerCount).map {
            Player(UUID.randomUUID(), "Player $it")
        }.toMutableList()

        // Spiel initialisieren
        val gameLogic = GameLogic(UUID.randomUUID(), players)
        gameLogic.initializeGame()

        // 🔎 Jeder Spieler sollte genau 8 Karten haben
        for (player in players) {
            val hand = gameLogic.getPlayerHand(player.playerId)
            assertNotNull(hand, "${player.name} hat keine Kartenhand erhalten")
            assertEquals(8, hand!!.cards.size, "${player.name} hat nicht genau 8 Karten")
        }

        // 🔎 Exploding Kittens im Deck = Spieleranzahl - 1
        val deckCards = gameLogic.drawPile.getCardsSnapshot()
        val kittenCount = deckCards.count { it.type == CardType.EXPLODING_KITTEN }
        assertEquals(
            playerCount - 1,
            kittenCount,
            "Exploding Kittens im Deck stimmen nicht bei $playerCount Spielern"
        )

        // 🔎 Gesamtkartenanzahl prüfen (Hand + Deck)
        val handCards = players.flatMap { gameLogic.getPlayerHand(it.playerId)!!.cards }
        val totalCards = handCards + deckCards

        assertTrue(totalCards.isNotEmpty(), "Gesamtkartenanzahl ist 0 – das kann nicht stimmen.")
    }
}