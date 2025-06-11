package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class GameInitializationTest {
    private lateinit var gameLogic: GameLogic
    private lateinit var players: MutableList<Player>

    @BeforeEach
    fun setUp() {
        players = (1..4).map {
            Player(UUID.randomUUID(), "Player $it")
        }.toMutableList()

        gameLogic = GameLogic(UUID.randomUUID(), players)
        gameLogic.initializeGame()
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4])
    fun `each player receives exactly 8 cards`(playerCount: Int) {
        val players = (1..playerCount).map {
            Player(UUID.randomUUID(), "Player $it")
        }.toMutableList()
        val gameLogic = GameLogic(UUID.randomUUID(), players)
        gameLogic.initializeGame()

        for (player in players) {
            val hand = gameLogic.getPlayerHand(player.playerId)
            assertNotNull(hand)
            assertEquals(8, hand!!.cards.size)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4])
    fun `deck contains exactly playerCount - 1 exploding kittens`(playerCount: Int) {
        val players = (1..playerCount).map {
            Player(UUID.randomUUID(), "Player $it")
        }.toMutableList()
        val gameLogic = GameLogic(UUID.randomUUID(), players)
        gameLogic.initializeGame()

        val kittenCount = gameLogic.drawPile.getCardsSnapshot()
            .count { it.type == CardType.EXPLODING_KITTEN }

        assertEquals(playerCount - 1, kittenCount)
    }

}