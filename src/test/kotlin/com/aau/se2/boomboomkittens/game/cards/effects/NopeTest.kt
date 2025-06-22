package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.PlayerLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class NopeTest {
    private lateinit var player0: Player
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var card: Card

    @BeforeEach
    fun setUp() {
        player0 = Player (playerId = UUID.randomUUID(), name = "Player0", defuseCount = 0, isAlive = true)
        player1 = Player (playerId = UUID.randomUUID(), name = "Player1", defuseCount = 0, isAlive = true)
        player2 = Player (playerId = UUID.randomUUID(), name = "Player2", defuseCount = 0, isAlive = true)
        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player0, player1, player2))
        cardLogic = CardLogic(2,gameLogic)
        card = Card(CardType.NOPE)
    }

    @Test
    fun nopeTest(){
        val effect = NopeEffect()

        println("Current Player: ${gameLogic.playerLogic.getCurrentPlayer()?.name}")

        effect.apply(card, player0, cardLogic)
        gameLogic.nextTurn()

        println("Current Player: ${gameLogic.playerLogic.getCurrentPlayer()?.name}")

        assertEquals(gameLogic.playerLogic.getCurrentPlayer(), player2)
    }
}