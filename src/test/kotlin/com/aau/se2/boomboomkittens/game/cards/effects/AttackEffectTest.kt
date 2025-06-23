package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.AttackEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.logic.GameLogicTest
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*
import org.junit.jupiter.api.Assertions.*


class AttackEffectTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var player3: Player
    private lateinit var attackCard: Card

    @BeforeEach
    fun setup(){
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        player3 = Player(UUID.randomUUID(), "Charlie")

        val lobbyId = UUID.randomUUID()
        val players = mutableListOf(player1, player2, player3)

        gameLogic = GameLogic(lobbyId, players)
        cardLogic = CardLogic(players.size, gameLogic)

        // Aktuellen Spieler setzen
        while (gameLogic.playerLogic.getCurrentPlayer() != player1){
            gameLogic.playerLogic.moveToNextPlayer()
        }

        // Beispielkarte
        attackCard = Card(CardType.ATTACK)

    }


    @Test
    fun `apply attack card should skip current players draw and give next player extra turn`(){
        val effect = AttackEffect()

        // Anwendung der Attack Karte
        effect.apply(attackCard, player1, cardLogic)

        // Spielerwechsel erwartet
        val currentPlayerAfter = gameLogic.playerLogic.getCurrentPlayer()
        assertEquals(player2, currentPlayerAfter, "Next player should be Bob")

        // Logischer Check, Alice sollte nicht mehr am Zug sein
        assertTrue(currentPlayerAfter != player1, "Alice should no longer be the current player")

    }

}