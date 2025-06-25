package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.SkipEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class SkipEffectTest {

    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var player3: Player
    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var skipEffect: SkipEffect
    private lateinit var skipCard: Card

    @BeforeEach
    fun setup(){
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        player3 = Player(UUID.randomUUID(), "Charlie")

        val players = mutableListOf(player1, player2, player3)
        gameLogic = GameLogic(UUID.randomUUID(), players)
        cardLogic = CardLogic(players.size, gameLogic)

        // Aktuellen Player auf Alice setzen
        while (gameLogic.playerLogic.getCurrentPlayer() != player1){
            gameLogic.playerLogic.moveToNextPlayer()
        }

        skipEffect = SkipEffect()
        skipCard = Card(CardType.SKIP)

    }

    @Test
    fun `apply skip card should move to next player`(){
        val currentBefore = gameLogic.playerLogic.getCurrentPlayer()
        skipEffect.apply(skipCard, player1, cardLogic)
        val currentAfter = gameLogic.playerLogic.getCurrentPlayer()

        assertEquals(player2, currentAfter, "Player should have been skipped to next player (Bob)")
        assert(currentAfter != currentBefore)
    }

}