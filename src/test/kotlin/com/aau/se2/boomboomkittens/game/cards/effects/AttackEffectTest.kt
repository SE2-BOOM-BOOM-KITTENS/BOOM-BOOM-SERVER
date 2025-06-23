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


class AttackEffectTest {

    private lateinit var attackEffect: AttackEffect
    private lateinit var mockCardLogic: CardLogic
    private lateinit var mockGameLogic: GameLogic
    private lateinit var testPlayer: Player
    private lateinit var testCard: Card

    @BeforeEach
    fun setUp(){
        attackEffect = AttackEffect()
        mockCardLogic = mock()
        mockGameLogic = mock()
        testPlayer = Player(name = "TestPlayer")
        testCard = Card(CardType.ATTACK)

        whenever(mockCardLogic.gameLogic).thenReturn(mockGameLogic)
    }

    @Test
    fun `apply should skip draw for current player and give extra turn to next player`(){

        attackEffect.apply(testCard, testPlayer, mockCardLogic)

        verify(mockGameLogic).skipDrawForCurrentPlayer()
        verify(mockGameLogic).giveExtraTurnToNextPlayer()
    }


}