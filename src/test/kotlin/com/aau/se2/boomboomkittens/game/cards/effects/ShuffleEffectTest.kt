package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.ShuffleEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertNotEquals


class ShuffleEffectTest{

    private lateinit var player: Player
    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var shuffleEffect: ShuffleEffect

    @BeforeEach
    fun setup(){
        player = Player(UUID.randomUUID(), "TestPlayer")
        val players = listOf(player)
        gameLogic = GameLogic(UUID.randomUUID(), players.toMutableList())
        cardLogic = CardLogic(players.size, gameLogic)
        shuffleEffect = ShuffleEffect()
    }

    @Test
    fun `apply should shuffle the deck`(){

        val cardsBeforeShuffle = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_HAIRY_POTATO),
            Card(CardType.DEFUSE),
            Card(CardType.EXPLODING_KITTEN),
        )
        cardsBeforeShuffle.forEach{cardLogic.drawPile.add(it)}

        val originalOrder = cardLogic.drawPile.getPileList().map {it.type}


        // act
        shuffleEffect.apply(Card(CardType.SHUFFLE), player, cardLogic)

        val shuffledOrder = cardLogic.drawPile.getPileList().map {it.type}


        // assert
        assertNotEquals(originalOrder, shuffledOrder, "Deck should be shuffled")

    }

}